package org.coffeegenerator.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.coffeegenerator.Coffee_Generator;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Apenas in-game");
        }
        else {
            if (cmd.getName().equalsIgnoreCase("generator")) {
                ItemStack item1 = new ItemStack(Material.STONE);
                ItemMeta meta = item1.getItemMeta();
                meta.setDisplayName(Coffee_Generator.getInstance().getConfig().getString("generator.name"));
                item1.setItemMeta(meta);
                ((Player) sender).getInventory().setItemInHand(item1);
            }
        }
        return false;
    }
}
