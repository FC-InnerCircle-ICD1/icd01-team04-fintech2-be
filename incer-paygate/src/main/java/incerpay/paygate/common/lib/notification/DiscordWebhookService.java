package incerpay.paygate.common.lib.notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DiscordWebhookService {

    private final DiscordWebhookClient discordWebhookClient;

    @Value("${discord.webhook.url}")
    private String webhookUrl;

    public DiscordWebhookService(DiscordWebhookClient discordWebhookClient) {
        this.discordWebhookClient = discordWebhookClient;
    }

    public void sendMessage(String content) {
        String payload = String.format("{\"content\":\"%s\"}", content);
        discordWebhookClient.sendMessage(payload);
    }
}

