package incerpay.paygate.common.lib.notification;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.MediaType;

@FeignClient(name = "discordWebhookClient", url = "${discord.webhook.url}")
public interface DiscordWebhookClient {

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    void sendMessage(@RequestBody String content);
}
