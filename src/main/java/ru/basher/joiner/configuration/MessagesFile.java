package ru.basher.joiner.configuration;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import ru.basher.joiner.util.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
public class MessagesFile {

	@Getter(AccessLevel.PRIVATE)
	private FileConfiguration cfg;
	@Getter(AccessLevel.PRIVATE)
	private final File file;

	private final List<String> noPerms = new ArrayList<>();
	private final List<String> joinNotFound = new ArrayList<>();

	private final List<String> adminUsage = new ArrayList<>();
	private final List<String> adminReloadSuccess = new ArrayList<>();

	private final List<String> setjoinmessageUsage = new ArrayList<>();
	private final List<String> setjoinmessageNoAccess = new ArrayList<>();
	private final List<String> setjoinmessageSuccess = new ArrayList<>();

	private final List<String> clearjoinmessageSuccess = new ArrayList<>();

	public MessagesFile(Plugin plugin) {
		this.file = new File(plugin.getDataFolder(), "messages.yml");
		createFile(plugin);
	}
	private void createFile(Plugin plugin) {
		if(!file.exists()) {
			plugin.saveResource("messages.yml", false);
		}
	}
	public void loadData() {
		cfg = YamlConfiguration.loadConfiguration(file);

		generate(noPerms, "noPerms");
		generate(joinNotFound, "joinNotFound");

		generate(adminUsage, "admin.usage");
		generate(adminReloadSuccess, "admin.reloadSuccess");

		generate(setjoinmessageUsage, "setjoinmessage.usage");
		generate(setjoinmessageNoAccess, "setjoinmessage.noAccess");
		generate(setjoinmessageSuccess, "setjoinmessage.success");

		generate(clearjoinmessageSuccess, "clearjoinmessage.success");
	}

	private void generate(List<String> list, String section) {
		list.clear();
		for(String s : cfg.getStringList(section)) {
			if(s == null) continue;
			list.add(TextUtils.toColor(s));
		}
	}
	
	
}
