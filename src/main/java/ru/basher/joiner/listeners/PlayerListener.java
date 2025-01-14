package ru.basher.joiner.listeners;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.basher.joiner.JoinerManager;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class PlayerListener implements Listener {

    private final JoinerManager joinerManager;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        CompletableFuture.runAsync(() -> joinerManager.onJoinAsync(player));
    }

}
