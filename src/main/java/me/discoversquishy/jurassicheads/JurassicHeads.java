package me.discoversquishy.jurassicheads;

import me.discoversquishy.jurassicheads.commands.CmdHeadReload;
import me.discoversquishy.jurassicheads.listeners.PlayerActivity;
import me.discoversquishy.jurassicheads.util.Lang;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class JurassicHeads extends JavaPlugin {
    private static JurassicHeads instance;

    @Override
    public void onEnable() {
        instance = this;

        registerCommands();
        registerConfig();
        registerListeners();
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static JurassicHeads getInstance() {
        return instance;
    }

    private void registerCommands() {
        getCommand("headreload").setExecutor(new CmdHeadReload());
    }

    private void registerConfig() {
        saveDefaultConfig();
        Lang.setFile(getConfig());
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new PlayerActivity(), this);
    }
}
