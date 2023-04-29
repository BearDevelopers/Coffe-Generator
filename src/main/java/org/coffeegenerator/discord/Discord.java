package org.coffeegenerator.discord;

import com.avaje.ebeaninternal.server.cluster.mcast.Message;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.event.Listener;

public class Discord extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        TextChannel channel = (TextChannel) e.getChannel();
        String message = e.getMessage().getContentRaw();
        if (message.startsWith("!")) {
            String[] args = message.substring(1).split(" ");
            String command = args[0];
            if (command.equalsIgnoreCase("sendmsg")) {
                channel.sendMessage("EZ").queue();
            }
        }
    }
}
