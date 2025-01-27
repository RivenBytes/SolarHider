package me.solar.solarHider.listeners;

import me.solar.solarHider.SolarHider;
import me.solar.solarHider.utils.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TabCompleteListener implements Listener {

    private final SolarHider plugin;

    public TabCompleteListener(SolarHider plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTabComplete(TabCompleteEvent event) {
        if (!(event.getSender() instanceof Player)) return;
        Player player = (Player) event.getSender();
        ConfigManager configManager = plugin.getConfigManager();

        if (player.isOp() || player.hasPermission("*")) return;

        String buffer = event.getBuffer();
        String[] args = buffer.split(" ");
        boolean isRootCommand = !buffer.contains(" ");

        List<String> allowedCommands = configManager.getAllowedTabCompletions(player);
        List<String> validCompletions = new ArrayList<>();

        if (isRootCommand) {
            //
            //
            // credits to ai for fixing bug
            validCompletions = allowedCommands.stream()
                    .filter(cmd -> plugin.getServer().getPluginCommand(cmd) != null)
                    .map(cmd -> "/" + cmd)
                    .collect(Collectors.toList());
        } else {
            String baseCommand = args[0];
            if (baseCommand.startsWith("/") && allowedCommands.contains(baseCommand.substring(1))) {
                Command command = plugin.getServer().getPluginCommand(baseCommand.substring(1));
                if (command instanceof PluginCommand) {
                    List<String> subCompletions = command.tabComplete(player, baseCommand.substring(1), args);
                    if (subCompletions != null) {
                        validCompletions.addAll(subCompletions);
                    }
                }
            }
        }


        event.setCompletions(validCompletions);
    }
}
