package me.jin.teleportbow;

import me.jin.teleportbow.commands.GetBow;
import me.jin.teleportbow.listeners.BowListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class TeleportBow extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        getCommand("getbow").setExecutor(new GetBow(this));
        getServer().getPluginManager().registerEvents(new BowListener(this), this);
    }

}
