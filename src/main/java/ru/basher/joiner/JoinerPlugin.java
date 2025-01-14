package ru.basher.joiner;

import lombok.Getter;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import ru.basher.joiner.commands.AdminCommand;
import ru.basher.joiner.commands.ClearjoinmessageCommand;
import ru.basher.joiner.commands.SetjoinmessageCommand;
import ru.basher.joiner.configuration.ConfigFile;
import ru.basher.joiner.configuration.MessagesFile;
import ru.basher.joiner.configuration.PlayersFile;
import ru.basher.joiner.listeners.PlayerListener;

@Getter
public class JoinerPlugin extends JavaPlugin {

    @Getter
    private static JoinerPlugin instance;

    public void onEnable() {
        instance = this;

        if(!createDir()) {
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        ConfigFile configFile = new ConfigFile(this);
        configFile.loadData();
        MessagesFile messagesFile = new MessagesFile(this);
        messagesFile.loadData();
        PlayersFile playersFile = new PlayersFile(this);
        playersFile.loadData();

        JoinerManager manager = new JoinerManager(configFile, playersFile);
        getServer().getPluginManager().registerEvents(new PlayerListener(manager), this);

        AdminCommand adminCommand = new AdminCommand(configFile, messagesFile, playersFile);
        PluginCommand adminPluginCommand = getCommand("basherjoiner");
        if(adminPluginCommand != null) {
            adminPluginCommand.setExecutor(adminCommand);
        }
        SetjoinmessageCommand setjoinmessageCommand = new SetjoinmessageCommand(configFile, messagesFile, playersFile);
        PluginCommand setjoinmessagePluginCommand = getCommand("setjoinmessage");
        if(setjoinmessagePluginCommand != null) {
            setjoinmessagePluginCommand.setExecutor(setjoinmessageCommand);
        }
        ClearjoinmessageCommand clearjoinmessageCommand = new ClearjoinmessageCommand(messagesFile, playersFile);
        PluginCommand clearjoinmessagePluginCommand = getCommand("clearjoinmessage");
        if(clearjoinmessagePluginCommand != null) {
            clearjoinmessagePluginCommand.setExecutor(clearjoinmessageCommand);
        }

        getServer().getConsoleSender().sendMessage("§a|---------§c" + getDescription().getName() + "§a--------");
        getServer().getConsoleSender().sendMessage("§a|Author: Aleks_Basher");
        getServer().getConsoleSender().sendMessage("§a|Version: " + getDescription().getVersion());
        getServer().getConsoleSender().sendMessage("§a| ");
        getServer().getConsoleSender().sendMessage("§a|Plugin Activated");
    }

    private boolean createDir() {
        if(!getDataFolder().exists() && !getDataFolder().mkdirs()) {
            getServer().getLogger().severe("Not created dir");
            return false;
        }
        return true;
    }

    public void onDisable( ) {
        getServer().getConsoleSender().sendMessage("§c|---------§6" + getDescription().getName() + "§c--------");
        getServer().getConsoleSender().sendMessage("§c|Plugin Disabled");
        getServer().getConsoleSender().sendMessage("§c|---------§6" + getDescription().getName() + "§c--------");
    }


}
