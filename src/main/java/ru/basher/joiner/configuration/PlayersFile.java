package ru.basher.joiner.configuration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayersFile {

	private FileConfiguration cfg;
	private final File file;

	private final Map<UUID, String> players = new ConcurrentHashMap<>();

	public PlayersFile(Plugin plugin) {
		this.file = new File(plugin.getDataFolder(), "players.yml");
		createFile(plugin);
	}
	private void createFile(Plugin plugin) {
		if(!file.exists()) {
			plugin.saveResource("players.yml", false);
		}
	}

	public synchronized void loadData() {
		cfg = YamlConfiguration.loadConfiguration(file);

		players.clear();
		for(String section : cfg.getConfigurationSection("players").getKeys(false)) {
			if(section == null) continue;
			String joinId = cfg.getString("players." + section);
			UUID uniqueId = UUID.fromString(section);
			players.put(uniqueId, joinId);
		}
	}

	@Nullable
	public synchronized String getJoinId(UUID playerUniqueId) {
		return players.get(playerUniqueId);
	}
	public synchronized void setPlayerJoin(@NotNull UUID playerUniqueId, @Nullable String joinId) {
		if(joinId == null) {
			if (cfg.contains("players." + playerUniqueId)) cfg.set("players." + playerUniqueId, null);
		} else {
			cfg.set("players." + playerUniqueId, joinId);
		}
		save();
	}

	private void save() {
		try {
			cfg.save(file);
		} catch (IOException ignored) {}
	}
	
	
}
