package com.example.discordbot.discord;

import com.example.discordbot.service.OllamaBotService;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

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

        if (!event.getAuthor().isBot() && (content.startsWith("!ia") || content.startsWith("!bot") || content.startsWith("!chat") || content.startsWith("!ai"))) {
            String command = content.split(" ")[0].trim();
            String question = content.replaceFirst(command, "").trim();

            // Envia uma mensagem inicial para indicar que o bot está "pensando"
            event.getChannel().sendTyping().queue();

            String answer = ollamaBotService.getModelResponse(command, question);

            // Divide a resposta em blocos de 2000 caracteres
            if (answer != null && answer.length() > 0) {
                for (int i = 0; i < answer.length(); i += 1999) {
                    int end = Math.min(answer.length(), i + 1999);
                    String chunk = answer.substring(i, end);
                    
                    // Envia cada parte da resposta para o canal
                    event.getChannel().sendMessage(chunk).queue();
                    
                    // Pequena pausa para evitar ser banido temporariamente pelo Discord por spam
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                event.getChannel().sendMessage("Desculpe, não consegui obter uma resposta.").queue();
            }
        }
    }
}
