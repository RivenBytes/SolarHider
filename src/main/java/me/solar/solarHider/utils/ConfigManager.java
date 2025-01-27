package me.solar.solarHider.utils;

import me.solar.solarHider.SolarHider;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConfigManager {

    private final SolarHider plugin;
    private FileConfiguration config;

    public ConfigManager(SolarHider plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public void reload() {
        plugin.reloadConfig();
        config = plugin.getConfig();
    }

    public String formatMessage(String message) {
        return message != null ? message.replace("&", "§") : "§cMessage not defined!";
    }

    public boolean isCommandAllowed(String command, Player player) {
        if (player.isOp() || player.hasPermission("*")) return true;

        Set<String> groups = getGroups();
        for (String group : groups) {
            String permission = config.getString("GROUPS." + group + ".PERMISSION");
            List<String> commands = config.getStringList("GROUPS." + group + ".COMMANDS");

            if (commands.contains(command) && player.hasPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    public List<String> getAllowedTabCompletions(Player player) {
        if (player.isOp() || player.hasPermission("*")) {
            return getAllTabCompleteOptions();
        }

        List<String> allowedCompletions = new ArrayList<>();
        Set<String> groups = getGroups();

        for (String group : groups) {
            String permission = config.getString("GROUPS." + group + ".PERMISSION");
            if (player.hasPermission(permission)) {
                allowedCompletions.addAll(config.getStringList("GROUPS." + group + ".TABCOMPLETE"));
            }
        }
        return allowedCompletions;
    }

    public Set<String> getGroups() {
        return config.getConfigurationSection("GROUPS").getKeys(false);
    }

    public List<String> getTabCompleteOptions(String group) {
        return config.getStringList("GROUPS." + group + ".TABCOMPLETE");
    }

    public List<String> getAllTabCompleteOptions() {
        List<String> allOptions = new ArrayList<>();
        Set<String> groups = getGroups();
        for (String group : groups) {
            allOptions.addAll(getTabCompleteOptions(group));
        }
        return allOptions;
    }
}
