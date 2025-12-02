package com.chalwk.config;

import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginConfig {
    private final Map<String, Object> settings = new HashMap<>();
    private final List<String> excludedCommands = new ArrayList<>();
    private final List<String> excludedPlayers = new ArrayList<>();
    private final List<String> excludedWorlds = new ArrayList<>();

    public void loadFromConfig(org.bukkit.configuration.ConfigurationSection config) {
        settings.put("global_toggle", config.getBoolean("global_toggle", true));
        settings.put("enabled_by_default", config.getBoolean("enabled_by_default", true));

        ConfigurationSection spy = config.getConfigurationSection("spy");
        if (spy != null) {
            for (String type : spy.getKeys(false)) {
                ConfigurationSection typeSection = spy.getConfigurationSection(type);
                if (typeSection != null) {
                    settings.put("spy." + type + ".enabled", typeSection.getBoolean("enabled", true));
                    settings.put("spy." + type + ".notification",
                            typeSection.getString("notification", "&7[&eBigBrother&7] &f{player} &7{action}"));
                    settings.put("spy." + type + ".permission",
                            typeSection.getString("permission", "bigbrother." + type + "spy.toggle"));
                }
            }
        }

        ConfigurationSection filters = config.getConfigurationSection("filters");
        if (filters != null) {
            excludedCommands.clear();
            excludedPlayers.clear();
            excludedWorlds.clear();

            excludedCommands.addAll(filters.getStringList("excluded_commands"));
            excludedPlayers.addAll(filters.getStringList("excluded_players"));
            excludedWorlds.addAll(filters.getStringList("excluded_worlds"));
        }

        ConfigurationSection messages = config.getConfigurationSection("messages");
        if (messages != null) {
            for (String key : messages.getKeys(false)) {
                settings.put("message." + key, messages.getString(key));
            }
        }
    }

    public boolean isSpyEnabled(String type) {
        return (boolean) settings.getOrDefault("spy." + type + ".enabled", true);
    }

    public String getSpyNotification(String type) {
        return (String) settings.getOrDefault("spy." + type + ".notification",
                "&7[&eBigBrother&7] &f{player} &7{action}");
    }

    public boolean isCommandExcluded(String command) {
        for (String excluded : excludedCommands) {
            if (command.toLowerCase().startsWith(excluded.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean isPlayerExcluded(String playerName) {
        return excludedPlayers.contains(playerName.toLowerCase());
    }

    public boolean isWorldExcluded(String worldName) {
        return excludedWorlds.contains(worldName);
    }
}