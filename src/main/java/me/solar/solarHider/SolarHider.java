package me.solar.solarHider;

import me.solar.solarHider.commands.SolarHiderCommand;
import me.solar.solarHider.listeners.CommandListener;
import me.solar.solarHider.listeners.TabCompleteListener;
import me.solar.solarHider.utils.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SolarHider extends JavaPlugin {

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        configManager = new ConfigManager(this);

        getCommand("solarhider").setExecutor(new SolarHiderCommand(this));
        getServer().getPluginManager().registerEvents(new CommandListener(this), this);
        getServer().getPluginManager().registerEvents(new TabCompleteListener(this), this);


        getLogger().info("SolarHider enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("SolarHider disabled.");
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
