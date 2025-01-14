package ru.basher.joiner.commands;

import lombok.AllArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.basher.joiner.configuration.MessagesFile;
import ru.basher.joiner.configuration.PlayersFile;

@AllArgsConstructor
public class ClearjoinmessageCommand implements CommandExecutor {

    private final MessagesFile messagesFile;
    private final PlayersFile playersFile;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage("§cThis command only for players");
            return true;
        }
        if(!player.hasPermission("basherjoiner.command.clearjoinmessage")) {
            for(String msg : messagesFile.getNoPerms()) player.sendMessage(msg);
            return true;
        }
        playersFile.setPlayerJoin(player.getUniqueId(), null);
        for(String msg : messagesFile.getClearjoinmessageSuccess()) player.sendMessage(msg);
        return true;
    }
}
