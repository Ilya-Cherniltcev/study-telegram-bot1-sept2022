package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.services.MessagesService;
import pro.sky.telegrambot.services.SheduleService;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);


    private boolean isSaidHello = false;
    @Autowired
    private TelegramBot telegramBot;
    private final MessagesService messagesService;
    private final SheduleService sheduleService;

    public TelegramBotUpdatesListener(MessagesService messagesService, SheduleService sheduleService) {
        this.messagesService = messagesService;
        this.sheduleService = sheduleService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            logger.debug("Processing update: {}", update);
            try {
                String textToUser = "";
                String userMessage = update.message().text();
                Long chatId = update.message().chat().id();
                //  --- запускаем обработку вводимого сообщения  -----------
                String result = messagesService.checkInputMessage(chatId, userMessage);
                // ------------------------------------
                if (result.equals("start") && !isSaidHello) {
                    textToUser = messagesService.sayHelloToUser(update);
                    isSaidHello = true;
                }
                // -----------------------------------
                if (result.equals("Incorrect")) {
                    textToUser = messagesService.sayAboutMistakeToUser();
                }
                // -----------------------------------
                if (result.equals("ok")) {
                    textToUser = "Всё ОК. Записано))";
                }
                printMessageToUser(chatId, textToUser);
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
            ;
        }
        ;
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    // -----------  печатаем пользователю сообщение ----------
    private void printMessageToUser(Long chatId, String messageText) {
        SendMessage message = new SendMessage(chatId, messageText);
        SendResponse response = telegramBot.execute(message);
    }

    // ----  запускаем метод каждую минуту ----------------
    @Scheduled(cron = "0 * * * * *")
    public void run() {
        Collection<NotificationTask> tasks = sheduleService.checkThisTime();
        // ***** Проверяем на null и empty  *******
        if (tasks != null && !tasks.isEmpty()) {
            // --- напоминаем пользователям про записанные задачи ----------
            for (NotificationTask t : tasks) {
                Long chatId = t.getId_chat();
                String remindMessage = t.getMessage();
                logger.info("Reminder: {} || {}", chatId, remindMessage);
                printMessageToUser(chatId, remindMessage);
            }
        }
    }
}
