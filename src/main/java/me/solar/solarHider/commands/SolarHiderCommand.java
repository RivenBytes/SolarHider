package me.solar.solarHider.commands;

import me.solar.solarHider.SolarHider;
import me.solar.solarHider.utils.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SolarHiderCommand implements CommandExecutor {

    private final SolarHider plugin;

    public SolarHiderCommand(SolarHider plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ConfigManager configManager = plugin.getConfigManager();

        if (args.length == 0) {
            sender.sendMessage("Usage: /solarhider reload");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("SOLARHIDER.RELOAD")) {
                sender.sendMessage(configManager.formatMessage(plugin.getConfig().getString("MESSAGES.NO-PERMISSION")));
                return true;
            }

            configManager.reload();
            sender.sendMessage(configManager.formatMessage(plugin.getConfig().getString("MESSAGES.RELOAD-SUCCESS")));
            return true;
        }

        sender.sendMessage(configManager.formatMessage(plugin.getConfig().getString("MESSAGES.UNKNOWN-COMMAND")));
        return true;
    }
}
