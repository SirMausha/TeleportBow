package me.jin.teleportbow.commands;

import me.jin.teleportbow.items.CustomBow;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class GetBow implements CommandExecutor {

    private final Plugin plugin;

    public GetBow(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            System.out.println("You need to be a player to use this command!");
            return false;
        }

        Player player = (Player) sender;
        CustomBow customBow = new CustomBow(plugin);

        player.getInventory().addItem(customBow);

        return true;
    }
}
