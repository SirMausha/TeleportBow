package me.jin.teleportbow.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Collections;

public class CustomBow extends ItemStack {

    public CustomBow(Plugin plugin) {
        super(Material.BOW);

        // Set item meta
        ItemMeta meta = getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lTeleport Bow"));
        meta.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&', "A mighty bow used to teleport!")));

        // set item to be unbreakable
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        // add glow to item
        meta.addEnchant(Enchantment.OXYGEN, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        NamespacedKey key = new NamespacedKey(plugin, "teleport_bow");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "teleport_bow");
        setItemMeta(meta);
    }
}
