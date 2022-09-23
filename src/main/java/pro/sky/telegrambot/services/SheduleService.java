package pro.sky.telegrambot.services;

import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repositories.NotivicationTaskRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

//@Component
@Service
public class SheduleService {
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final NotivicationTaskRepository notivicationTaskRepository;

    public SheduleService(NotivicationTaskRepository notivicationTaskRepository) {
        this.notivicationTaskRepository = notivicationTaskRepository;
    }

    public Collection<NotificationTask> checkThisTime() {
        logger.info("*** Запущен метод checkThisTime() ***");
        LocalDateTime localDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        logger.info("Time is " + localDateTime);
        // ***** сравниваем текущее время с записями в БД, *******
        // *****  и возвращаем все записи из БД со временем, равным текущему *******
        //Collection<NotificationTask> tasks = notivicationTaskRepository.findAllByLocalDateTime();
        Collection<NotificationTask> tasks = notivicationTaskRepository.findNotificationTasksByDateTimeBefore(localDateTime);
        logger.info("Nowtime tasks are: " + tasks);
        return tasks;
    }

    public void removeTask(NotificationTask notificationTask) {
        try {
            notivicationTaskRepository.delete(notificationTask);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        logger.info("***  Запись удалена: " + notificationTask);
    }
}