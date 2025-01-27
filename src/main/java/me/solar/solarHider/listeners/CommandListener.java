package me.solar.solarHider.listeners;

import me.solar.solarHider.SolarHider;
import me.solar.solarHider.utils.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.entity.Player;

public class CommandListener implements Listener {

    private final SolarHider plugin;

    public CommandListener(SolarHider plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage().split(" ")[0].substring(1).toLowerCase();
        Player player = event.getPlayer();
        ConfigManager configManager = plugin.getConfigManager();

        if (player.isOp() || player.hasPermission("*")) return;

        if (!configManager.isCommandAllowed(command, player)) {
            player.sendMessage(configManager.formatMessage(plugin.getConfig().getString("MESSAGES.NO-COMMAND")));
            event.setCancelled(true);
        }
    }
}
