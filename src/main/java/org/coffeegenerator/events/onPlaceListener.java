package org.coffeegenerator.events;

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

    private final FileConfiguration config = Coffee_Generator.getInstance().getConfig();
    private static BukkitTask countdownTask;
    private final int countdownSeconds = config.getInt("generator.seconds-of-spawn");
    private long countdown = countdownSeconds * 20L;
    private boolean set = false;

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Block block = e.getBlockPlaced();
        Location loc = block.getLocation();

        if (block.getType() == Material.STONE) {
            block.setType(Material.DIAMOND_BLOCK);
            double y = block.getY() + 4.0f;
            p.sendMessage(ChatColor.RED + "Bloco colocado! Gerando itens...");
            Location hologramLocation = new Location(block.getWorld(), block.getX() + 0.5, y, block.getZ() + 0.5);
            Hologram hologram = Coffee_Generator.holographicDisplaysAPI.createHologram(hologramLocation);
            set = true;
            Bukkit.getScheduler().runTaskTimer(Coffee_Generator.getInstance(), () -> {
            }, 0L, 20L * countdownSeconds);

            if (set) {
                countdownTask = new BukkitRunnable() {
                    @Override
                    public void run() {
                        countdown--;
                        if (countdown <= 0) {
                            hologram.getLines().clear();
                            hologram.getLines().appendItem(new ItemStack(Material.DIAMOND_ORE));
                            hologram.getLines().appendText("Gerado um novo item!");
                            for (int i = 0; i < config.getInt("generator.items-drops"); i++) {
                                loc.getWorld().dropItemNaturally(hologramLocation, new ItemStack(Material.DIAMOND_ORE));
                            }
                        } else {
                            hologram.getLines().clear();
                            hologram.getLines().appendItem(new ItemStack(Material.DIAMOND_ORE));
                            hologram.getLines().appendText("Gerando novo item em " + (countdown / 20) + " segundos");
                        }
                    }
                }.runTaskTimer(Coffee_Generator.getInstance(), 20L * countdownSeconds, 20L);
            }
        }
    }
}
