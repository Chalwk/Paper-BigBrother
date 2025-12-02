package com.chalwk.managers;

import com.chalwk.util.SpyType;
import org.bukkit.entity.Player;

import java.util.*;

public class SpyManager {

    private final Map<UUID, Set<SpyType>> playerSpies = new HashMap<>();
    private final Map<UUID, Boolean> globalToggles = new HashMap<>();

    public SpyManager() {

    }

    public boolean toggleGlobal(Player player) {
        UUID playerId = player.getUniqueId();
        boolean currentState = globalToggles.getOrDefault(playerId, false);
        boolean newState = !currentState;
        globalToggles.put(playerId, newState);
        return newState;
    }

    public void toggleSpyForPlayer(Player target, SpyType type) {
        UUID targetId = target.getUniqueId();
        Set<SpyType> spySet = playerSpies.computeIfAbsent(targetId, k -> new HashSet<>());

        if (spySet.contains(type)) {
            spySet.remove(type);
        } else {
            spySet.add(type);
        }

        if (spySet.isEmpty()) {
            playerSpies.remove(targetId);
        }
    }

    public void toggleSpy(Player player, SpyType type) {
        UUID playerId = player.getUniqueId();
        Set<SpyType> spySet = playerSpies.computeIfAbsent(playerId, k -> new HashSet<>());

        if (spySet.contains(type)) {
            spySet.remove(type);
        } else {
            spySet.add(type);
        }

        if (spySet.isEmpty()) {
            playerSpies.remove(playerId);
        }
    }

    public Set<SpyType> getEnabledSpies(Player player) {
        return playerSpies.getOrDefault(player.getUniqueId(), new HashSet<>());
    }

    public String getStatusMessage(Player player) {
        Set<SpyType> enabledSpies = getEnabledSpies(player);

        if (enabledSpies.isEmpty()) {
            return "&7No spy features are currently enabled.";
        }

        StringBuilder builder = new StringBuilder("&aEnabled spy features:");
        for (SpyType type : enabledSpies) {
            builder.append("\n&7- &e").append(type.getCommand());
        }
        return builder.toString();
    }

    public boolean isSpyEnabled(Player player, SpyType type) {
        if (!globalToggles.getOrDefault(player.getUniqueId(), true)) {
            return false;
        }

        Set<SpyType> spySet = playerSpies.get(player.getUniqueId());
        return spySet != null && spySet.contains(type);
    }
}