package ru.basher.joiner;

import lombok.AllArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.basher.joiner.configuration.ConfigFile;
import ru.basher.joiner.configuration.PlayersFile;
import ru.basher.joiner.data.JoinValue;
import ru.basher.joiner.util.TextUtils;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class JoinerManager {

    private final ConfigFile configFile;
    private final PlayersFile playersFile;

    public void onJoinAsync(Player player) {
        String joinId = playersFile.getJoinId(player.getUniqueId());
        if(joinId == null) return;
        JoinValue joinValue = configFile.getJoins().get(joinId);
        if(joinValue == null) return;

        List<String> temp = new ArrayList<>();
        for(String s : joinValue.getMessage()) temp.add(s.replace("{playerName}", player.getName()));
        List<String> message = PlaceholderAPI.setPlaceholders(player, temp);
        message.replaceAll(TextUtils::toColor);

        Bukkit.getScheduler().runTask(JoinerPlugin.getInstance(), ()-> {
            for(Player pl : Bukkit.getOnlinePlayers()) {
                if(pl == null) continue;
                for(String msg : message) pl.sendMessage(msg);
            }
        });
    }

}
