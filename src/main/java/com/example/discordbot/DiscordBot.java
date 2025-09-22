package com.example.discordbot.discord;

import com.example.discordbot.service.OllamaBotService;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

@Component
public class DiscordBot extends ListenerAdapter {

    private final OllamaBotService ollamaBotService;

    public DiscordBot(OllamaBotService ollamaBotService) {
        this.ollamaBotService = ollamaBotService;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String content = message.getContentRaw();

        System.out.println("Received: " + content);

        if (!event.getAuthor().isBot() && content.startsWith("!ia")) {
            String command = content.split(" ")[0].trim();
            String question = content.replaceFirst(command, "").trim();

            String answer = ollamaBotService.getModelResponse(command, question);
            event.getChannel().sendMessage(answer).queue();
        }
    }
}
