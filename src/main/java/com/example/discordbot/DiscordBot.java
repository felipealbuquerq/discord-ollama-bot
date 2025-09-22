package com.example.discordbot.discord;

import com.example.discordbot.service.OllamaBotService;
import jakarta.annotation.PostConstruct;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DiscordBot extends ListenerAdapter {

    private final OllamaBotService ollamaBotService;

    @Value("${discord.token}")
    private String discordToken;

    public DiscordBot(OllamaBotService ollamaBotService) {
        this.ollamaBotService = ollamaBotService;
    }

    @PostConstruct
    public void startBot() throws Exception {
        JDABuilder.createDefault(discordToken)
                .addEventListeners(this)
                .build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String content = message.getContentRaw();

        if (!event.getAuthor().isBot() && content.startsWith("!ia")) {
            String model = "deepseek-r1:7b";
            String question = content.replaceFirst("!ia", "").trim();

            if (content.startsWith("!ia:coder")) {
                model = "deepseek-coder:6.7b";
                question = content.replaceFirst("!ia:coder", "").trim();
            } else if (content.startsWith("!ia:r1")) {
                model = "deepseek-r1:7b";
                question = content.replaceFirst("!ia:r1", "").trim();
            }

            String answer = ollamaBotService.getModelResponse(model, question);
            event.getChannel().sendMessage(answer).queue();
        }
    }
}
