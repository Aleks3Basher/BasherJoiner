package ru.basher.joiner.commands;

import lombok.AllArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.basher.joiner.configuration.ConfigFile;
import ru.basher.joiner.configuration.MessagesFile;
import ru.basher.joiner.configuration.PlayersFile;
import ru.basher.joiner.data.JoinValue;

@AllArgsConstructor
public class SetjoinmessageCommand implements CommandExecutor {

    private final ConfigFile configFile;
    private final MessagesFile messagesFile;
    private final PlayersFile playersFile;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage("§cThis command only for players");
            return true;
        }
        if(!player.hasPermission("basherjoiner.command.setjoinmessage")) {
            for(String msg : messagesFile.getNoPerms()) player.sendMessage(msg);
            return true;
        }
        if(args.length == 0) {
            for(String msg : messagesFile.getSetjoinmessageUsage()) player.sendMessage(msg);
            return true;
        }
        JoinValue joinValue = configFile.getJoins().get(args[1]);
        if(joinValue == null) {
            for(String msg : messagesFile.getJoinNotFound()) player.sendMessage(msg);
            return true;
        }
        if(!player.hasPermission(joinValue.getPermission())) {
            for(String msg : messagesFile.getSetjoinmessageNoAccess()) player.sendMessage(msg);
            return true;
        }
        playersFile.setPlayerJoin(player.getUniqueId(), joinValue.getId());
        for(String msg : messagesFile.getSetjoinmessageSuccess()) player.sendMessage(msg);
        return true;
    }
}
