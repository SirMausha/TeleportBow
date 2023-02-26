package me.jin.teleportbow.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.UUID;

public class BowListener implements Listener {

private final Plugin plugin;


    public BowListener(Plugin plugin) {
        this.plugin = plugin;
    }

    //The key is the Player, and the long is the epoch time of the last time they ran the command
    private HashMap<UUID, Long> cooldowns = new HashMap<>();

    @EventHandler
    public void onBowLeftClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null) return;
        if (item.getType() != Material.BOW) return;

        // gets item data and checks if it is a teleport bow
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(plugin, "teleport_bow");
        String value = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);

        if (value == null) return;
        if (!value.equals("teleport_bow")) return;

        // Cancel right click event
        if (event.getAction().toString().contains("RIGHT")) {
            event.setCancelled(true);
        }

        // Stop player from breaking blocks with the bow
        if (event.getAction().toString().contains("LEFT")) {
            event.setCancelled(true);
        }

        // checks if player is on cooldown
        if (!cooldowns.containsKey(player.getUniqueId()) || System.currentTimeMillis() - cooldowns.get(player.getUniqueId()) > 5000) { // 5 seconds
            cooldowns.put(player.getUniqueId(), System.currentTimeMillis());

            // Shoots arrow if the player left clicks and sets player as the shooter
            if (event.getAction().toString().contains("LEFT")) {
                Arrow arrow = player.launchProjectile(Arrow.class);
                arrow.setShooter(player);
                arrow.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
            }
        } else if (event.getAction().toString().contains("RIGHT")) {
            // Teleports player to the arrow if the player right clicks only if the shooter is the player
            for (Entity entity : player.getNearbyEntities(50, 50, 50)) {
                if (entity instanceof Arrow && ((Arrow) entity).getShooter() == player) {
                    // Teleports the player to the arrow without changing the pitch and yaw
                    Location arrowLocation = entity.getLocation();
                    player.teleport(new Location(arrowLocation.getWorld(), arrowLocation.getX(), arrowLocation.getY(), arrowLocation.getZ(), player.getLocation().getYaw(), player.getLocation().getPitch()));

                    // Removes the arrow
                    entity.remove();
                }
            }
        } else {
            player.sendMessage("You are on cooldown!");
        }
    }
}
