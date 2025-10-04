package com.example.discordbot.discord;

import com.example.discordbot.discord.DiscordBot;

import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import net.dv8tion.jda.api.requests.GatewayIntent;

@Configuration
public class DiscordBotConfig {

    @Value("${discord.token}")
    private String discordToken;

    private final DiscordBot discordBot;

    public DiscordBotConfig(DiscordBot discordBot) {
        this.discordBot = discordBot;
    }

    @PostConstruct
    public void startBot() throws Exception {
        JDABuilder.createDefault(discordToken)
            .enableIntents(GatewayIntent.MESSAGE_CONTENT)
            .addEventListeners(discordBot)
            .build();
    }
}
