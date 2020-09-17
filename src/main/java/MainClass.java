
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

public class MainClass {

    public static void main(String[] args) {
        final String token = "TOKEN";
        final DiscordClient client = DiscordClient.create(token);
        final GatewayDiscordClient gateway = client.login().block();
        String badWords[] = {"fuck", "mc", "bc", "bitch"};
        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            final MessageChannel channel = message.getChannel().block();
            final String user = event.getMessage().getUserData().username();

            for (int i = 0; i < badWords.length; i++) {
                if (message.getContent().contains(badWords[i])) {
                    channel.createMessage("@" + user + " Dont say that! its bad").block();
                    event.getMessage().delete().block();
                }
            }
        });
        gateway.onDisconnect().block();
    }
}
