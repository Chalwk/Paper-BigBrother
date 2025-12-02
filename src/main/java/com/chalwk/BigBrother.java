package com.chalwk;

import com.chalwk.commands.BigBrotherCommand;
import com.chalwk.config.ConfigManager;
import com.chalwk.listeners.SpyListener;
import com.chalwk.managers.SpyManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BigBrother extends JavaPlugin {

    private ConfigManager configManager;
    private SpyManager spyManager;

    @Override
    public void onEnable() {
        this.configManager = new ConfigManager(this);
        this.spyManager = new SpyManager();

        configManager.loadConfig();

        getCommand("bigbrother").setExecutor(new BigBrotherCommand(this));
        getServer().getPluginManager().registerEvents(new SpyListener(this), this);

        getLogger().info("BigBrother enabled! Watching player activities...");
    }

    @Override
    public void onDisable() {
        getLogger().info("BigBrother disabled!");
    }

    public void reload() {
        configManager.reloadConfig();
        getLogger().info("Configuration reloaded!");
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public SpyManager getSpyManager() {
        return spyManager;
    }
}