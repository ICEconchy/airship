package uk.co.airship;

import org.bukkit.plugin.java.JavaPlugin;

public final class Airship extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
getLogger().info("Airship has been enabled!");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
