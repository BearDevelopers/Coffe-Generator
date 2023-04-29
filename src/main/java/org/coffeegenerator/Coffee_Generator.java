package org.coffeegenerator;

import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.coffeegenerator.commands.Commands;
import org.coffeegenerator.discord.Discord;
import org.coffeegenerator.events.onPlaceListener;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Coffee_Generator extends JavaPlugin {
    private static Coffee_Generator instance;
    public static JDA jda = JDABuilder.createDefault("MTAxNzQyMzE2MDQ2OTEwNjc2OQ.GEjrNj.U-KTv6uWebFaHl05NLSeq7sMM9dusF8lmhg0M4").build();
    private static final long id = 1101860580425469992L;
    public static TextChannel channel = jda.getTextChannelById(id);
    public static HolographicDisplaysAPI holographicDisplaysAPI;

    public void enviableMessage(int i) {
        EmbedBuilder builder = new EmbedBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dateStr = sdf.format(new Date());
        if (channel == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Não encontrado");
        } else {
            if (i == 1) {
                int playersonline = Bukkit.getServer().getOnlinePlayers().size();
                builder.setTitle("Log")
                        .setDescription("O plugin foi habilitado no servidor: " + Bukkit.getServer().getIp())
                        .setColor(Color.RED)
                        .addField("Ip:",Bukkit.getServer().getIp(), true)
                        .addField("Name:",Bukkit.getServer().getServerName(), true)
                        .addField("Players Online:", String.valueOf(playersonline), true)
                        .addField("Versão", Bukkit.getServer().getBukkitVersion(), true)
                        .addField("Iniciado em", dateStr, true);

                MessageEmbed embed = builder.build();
                channel.sendMessageEmbeds(embed).queue();
            }
            else if(i == 2) {
                Bukkit.getConsoleSender().sendMessage("Conexão realizada com sucesso!");
            }
            else if (i == 3 ) {
                Bukkit.getConsoleSender().sendMessage("Bot foi desabilitado!");
            }
        }
    }

    @Override
    public void onEnable() {
        try {
            jda.getPresence().setActivity(Activity.playing("Em desenvolvimento. Feito em java!"));
            jda.addEventListener(new Discord());

            channel = jda.getTextChannelById(id);
            if (channel == null) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Erro ao realizar conexão com o canal!");
            } else {
                enviableMessage(1);
            }
        } catch (Exception e) {
            getLogger().severe("Não foi possível conectar ao Discord: " + e.getMessage());
            enviableMessage(2);
        }

        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            getLogger().severe("*** HolographicDisplays is not installed or not enabled. ***");
            getLogger().severe("*** This plugin will be disabled. ***");
            this.setEnabled(false);
            return;
        }

        instance = this;
        saveDefaultConfig();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Iniciado com sucesso");
        PluginManager pl = Bukkit.getPluginManager();
        pl.registerEvents(new onPlaceListener(), this);
        getCommand("generator").setExecutor(new Commands());
        holographicDisplaysAPI = HolographicDisplaysAPI.get(this);
    }

    public static Coffee_Generator getInstance() {
        return instance;
    }



    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Desabilitado com sucesso");
        enviableMessage(3);
    }
}
