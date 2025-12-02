package com.chalwk.commands;

import com.chalwk.BigBrother;
import com.chalwk.util.MessageHelper;
import com.chalwk.util.SpyType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record BigBrotherCommand(BigBrother plugin) implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (!sender.hasPermission("bigbrother.use")) {
            MessageHelper.sendMessage(sender, "&cYou don't have permission to use BigBrother!");
            return true;
        }

        if (args.length == 0) {
            if (!(sender instanceof Player player)) {
                MessageHelper.sendMessage(sender, "&cOnly players can use this command!");
                return true;
            }

            boolean newState = plugin.getSpyManager().toggleGlobal(player);
            String message = newState ?
                    "&aBigBrother enabled! Use subcommands to configure specific spy features." :
                    "&cBigBrother disabled! All spy features are now off.";
            MessageHelper.sendMessage(sender, message);
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "reload":
                if (!sender.hasPermission("bigbrother.reload")) {
                    MessageHelper.sendMessage(sender, "&cYou don't have permission to reload BigBrother!");
                    return true;
                }
                plugin.reload();
                MessageHelper.sendMessage(sender, "&aConfiguration reloaded!");
                return true;

            case "status":
                if (!(sender instanceof Player player)) {
                    MessageHelper.sendMessage(sender, "&cOnly players can check their status!");
                    return true;
                }
                String status = plugin.getSpyManager().getStatusMessage(player);
                MessageHelper.sendMessage(sender, status);
                return true;

            default:
                SpyType spyType = SpyType.fromCommand(subCommand);
                if (spyType != null) {
                    handleSpyToggle(sender, args, spyType);
                    return true;
                }

                MessageHelper.sendMessage(sender, "&cUnknown command. Use /bigbrother for help.");
                return true;
        }
    }

    private void handleSpyToggle(CommandSender sender, String[] args, SpyType spyType) {
        if (!(sender instanceof Player player)) {
            MessageHelper.sendMessage(sender, "&cOnly players can toggle spy features!");
            return;
        }

        if (!player.hasPermission(spyType.getPermission())) {
            MessageHelper.sendMessage(sender, "&cYou don't have permission to use this spy feature!");
            return;
        }

        if (args.length > 1) {
            if (!player.hasPermission(spyType.getPermissionOthers())) {
                MessageHelper.sendMessage(sender, "&cYou don't have permission to toggle spy features for others!");
                return;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                MessageHelper.sendMessage(sender, "&cPlayer not found!");
                return;
            }

            plugin.getSpyManager().toggleSpyForPlayer(target, spyType);
            boolean enabled = plugin.getSpyManager().getEnabledSpies(target).contains(spyType);
            String state = enabled ? "enabled" : "disabled";
            MessageHelper.sendMessage(sender,
                    "&a" + spyType.getCommand() + " spy has been " + state + " for " + target.getName() + "!");
        } else {
            plugin.getSpyManager().toggleSpy(player, spyType);
            boolean enabled = plugin.getSpyManager().getEnabledSpies(player).contains(spyType);
            String state = enabled ? "enabled" : "disabled";
            MessageHelper.sendMessage(sender, "&a" + spyType.getCommand() + " spy has been " + state + "!");
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender,
                                      @NotNull Command command,
                                      @NotNull String label,
                                      @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            String partial = args[0].toLowerCase();

            List<String> commands = Arrays.asList("reload", "status");
            for (String cmd : commands) {
                if (cmd.startsWith(partial)) {
                    if (cmd.equals("reload") && sender.hasPermission("bigbrother.reload")) {
                        completions.add(cmd);
                    } else if (!cmd.equals("reload")) {
                        completions.add(cmd);
                    }
                }
            }

            for (SpyType type : SpyType.values()) {
                if (type.getCommand().startsWith(partial) &&
                        (sender instanceof Player player && player.hasPermission(type.getPermission()))) {
                    completions.add(type.getCommand());
                }
            }

        } else if (args.length == 2) {
            SpyType spyType = SpyType.fromCommand(args[0]);
            if (spyType != null && sender.hasPermission(spyType.getPermissionOthers())) {
                String partial = args[1].toLowerCase();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getName().toLowerCase().startsWith(partial)) {
                        completions.add(player.getName());
                    }
                }
            }
        }

        return completions;
    }
}