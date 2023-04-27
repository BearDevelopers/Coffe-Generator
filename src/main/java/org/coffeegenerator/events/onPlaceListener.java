package org.coffeegenerator.events;

import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
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
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.coffeegenerator.Coffee_Generator;

public class onPlaceListener implements Listener {

    FileConfiguration config = Coffee_Generator.getInstance().getConfig();
    private BukkitTask countdownTask;
    private int countdownSeconds = config.getInt("generator.segunds-of-spawn");
    private long countdown = countdownSeconds * 20L;

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Block blocks = e.getBlockPlaced();
        Location loc = blocks.getLocation();
        if (e.getBlockPlaced().getType() == Material.STONE) {
            loc.getBlock().setType(Material.DIAMOND_BLOCK);
            double y = blocks.getY() + 0.5f;
            p.sendMessage(ChatColor.RED + "Bloco colocado!, Gerando itens...");
            Location locs = new Location(blocks.getWorld(), blocks.getX(), y, blocks.getZ());
            Hologram hologram = Coffee_Generator.holographicDisplaysAPI.createHologram(locs);
            Bukkit.getScheduler().runTaskTimer(Coffee_Generator.getInstance(), () -> {
                for (int i = 0; i < config.getInt("generator.itens-drops"); i++) {
                    loc.getWorld().dropItemNaturally(locs, new ItemStack(Material.DIAMOND_ORE));
                }
                countdownTask = new BukkitRunnable() {
                    @Override
                    public void run() {
                        countdown--;
                        if (countdown <= 0) {
                            hologram.getLines().appendItem(new ItemStack(Material.DIAMOND_ORE));
                            hologram.getLines().appendText("Gerado um novo item!");
                            countdown = countdownSeconds * 20L;
                        } else {
                            hologram.getLines().clear();
                            hologram.getLines().appendItem(new ItemStack(Material.DIAMOND_ORE));
                            hologram.getLines().appendText("Ira gerar um novo item em " + (countdown/20) + "segundos");
                        }
                    }
                }.runTaskTimer(Coffee_Generator.getInstance(), 20L, 20L);
                loc.getWorld().dropItem(locs, new ItemStack(Material.DIAMOND_ORE));
            }, 0L, 20L * config.getInt("generator.segunds-of-spawn"));
        }
    }
}