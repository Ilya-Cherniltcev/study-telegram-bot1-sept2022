package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        Integer lastUpdateId;
        Integer preLastUpdateId = null;
        for (Update update : updates) {
            logger.info("Processing update: {}", update);
            // Process your updates here
            try {
                lastUpdateId = update.updateId();
                logger.info("update.updateId() " + lastUpdateId);
                if (update.message().text().equals("/start") && preLastUpdateId == null) {
                    preLastUpdateId = lastUpdateId;
                    Long chatId = update.message().chat().id();
                    String user = update.message().chat().firstName()
                            != null ? update.message().chat().firstName() : "user";
                    String messageText = "Hello " + user +
                            "! I'm the sapros_study1_bot)). Good day to you!";
                    SendMessage message = new SendMessage(chatId, messageText);
                    SendResponse response = telegramBot.execute(message);
                }
            }
            catch(NullPointerException e){
                System.out.println(e.getMessage());
            };
        };

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
