package uk.co.airship;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;


public class Vehicle extends JavaPlugin implements Listener {
    private Map<Player, ArmorStand> drivingMap;

    @Override
    public void onEnable() {
        // Register events
        getServer().getPluginManager().registerEvents(this, this);

        // Initialize the driving map
        drivingMap = new HashMap<>();
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();

        // Check if the entity is an armor stand
        if (entity instanceof ArmorStand) {
            ArmorStand armorStand = (ArmorStand) entity;

            // Set the player in "driving" mode for the armor stand
            drivingMap.put(event.getPlayer(), armorStand);

            // Prevent armor stand interaction, if desired
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        // Check if the player is in "driving" mode
        if (drivingMap.containsKey(player)) {
            ArmorStand armorStand = drivingMap.get(player);

            // Calculate movement direction and speed
            Location from = event.getFrom();
            Location to = event.getTo();
            Vector direction = to.toVector().subtract(from.toVector()).multiply(0.1); // Adjust the multiplier for desired speed

            // Update armor stand's position and rotation
            armorStand.teleport(armorStand.getLocation().add(direction));
            armorStand.setHeadPose(new EulerAngle(-Math.toRadians(event.getTo().getYaw()), 0, 0)); // Adjust the Euler angles for desired rotation

            // Prevent the player from moving
            event.setCancelled(true);
        }
    }

}

