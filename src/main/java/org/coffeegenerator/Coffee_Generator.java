package org.coffeegenerator;

import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.coffeegenerator.commands.Commands;
import org.coffeegenerator.events.onPlaceListener;

public final class Coffee_Generator extends JavaPlugin {
    private static Coffee_Generator instance;
    public static HolographicDisplaysAPI holographicDisplaysAPI;

    @Override
    public void onEnable() {
        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
            getLogger().severe("*** This plugin will be disabled. ***");
            this.setEnabled(false);
            return;
        }

        instance=this;
        saveDefaultConfig();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Iniciado com sucesso");
        PluginManager pl = Bukkit.getPluginManager();
        pl.registerEvents(new onPlaceListener(), this);
        getCommand("generator").setExecutor(new Commands());
        holographicDisplaysAPI = HolographicDisplaysAPI.get(this);
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Desabilitado com sucesso");
    }
    public static Coffee_Generator getInstance() {
        return instance;
    }
}
