package org.coffeegenerator.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;
import org.coffeegenerator.Coffee_Generator;

public class onPlaceListener implements Listener {
    public BukkitScheduler scheduler;
    FileConfiguration config = Coffee_Generator.getInstance().getConfig();
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Block blocks = e.getBlockPlaced();
        Location loc = blocks.getLocation();
        if (p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(config.getString("generator.name"))) {
            loc.getBlock().setType(Material.DIAMOND);
            double y = blocks.getY() + 2;
            Location locs = new Location(blocks.getWorld(), blocks.getX(), y, blocks.getZ());
            scheduler.runTaskTimer(Coffee_Generator.getInstance(), new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i > config.getInt("generator.itens-drops"); i++) {
                        loc.getWorld().dropItemNaturally(locs, new ItemStack(Material.DIAMOND_ORE));
                    }
                }
            }, 0L, 20L * config.getInt("generator.segunds-of-spawn"));
            scheduler = Bukkit.getScheduler();
            loc.getWorld().dropItem(locs, new ItemStack(Material.DIAMOND_ORE));
        }
    }
}
