package ru.basher.joiner.configuration;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import ru.basher.joiner.data.JoinValue;
import ru.basher.joiner.util.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class ConfigFile {

	@Getter(AccessLevel.PRIVATE)
	private FileConfiguration cfg;
	@Getter(AccessLevel.PRIVATE)
	private final File file;

	private final Map<String, JoinValue> joins = new ConcurrentHashMap<>();

	public ConfigFile(Plugin plugin) {
		this.file = new File(plugin.getDataFolder(), "config.yml");
		createFile(plugin);
	}
	private void createFile(Plugin plugin) {
		if(!file.exists()) {
			plugin.saveResource("config.yml", false);
		}
	}
	public void loadData() {
		cfg = YamlConfiguration.loadConfiguration(file);

		joins.clear();
		for(String section : cfg.getConfigurationSection("joins").getKeys(false)) {
			if(section == null) continue;
			String permission = cfg.getString("joins." + section + ".permission");
			List<String> message = new ArrayList<>();
			for(String s : cfg.getStringList("joins." + section + ".message")) {
				if(s == null) continue;
				message.add(TextUtils.toColor(s));
			}
			joins.put(section, new JoinValue(section, permission, message));
		}
	}
	
	
}
