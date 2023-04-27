package org.coffeegenerator.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.coffeegenerator.Coffee_Generator;

public class onPlaceListener implements Listener {

    FileConfiguration config = Coffee_Generator.getInstance().getConfig();

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Block blocks = e.getBlockPlaced();
        Location loc = blocks.getLocation();
        if (e.getBlockPlaced().getType() == Material.STONE) {
            loc.getBlock().setType(Material.DIAMOND_BLOCK);
            double y = blocks.getY() + 2;
            p.sendMessage(ChatColor.RED + "Bloco colocado!, Gerando itens...");
            Location locs = new Location(blocks.getWorld(), blocks.getX(), y, blocks.getZ());
            Bukkit.getScheduler().runTaskTimer(Coffee_Generator.getInstance(), () -> {
                for (int i = 0; i < config.getInt("generator.itens-drops"); i++) {
                    loc.getWorld().dropItemNaturally(locs, new ItemStack(Material.DIAMOND_ORE));
                }
            }, 0L, 20L * config.getInt("generator.segunds-of-spawn"));
            loc.getWorld().dropItem(locs, new ItemStack(Material.DIAMOND_ORE));
        }
    }
}
