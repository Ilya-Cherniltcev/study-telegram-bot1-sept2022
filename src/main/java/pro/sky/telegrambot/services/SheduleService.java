package pro.sky.telegrambot.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repositories.NotivicationTaskRepository;

import java.time.LocalDateTime;
import java.util.Collection;

@Component
public class SheduleService {
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final NotivicationTaskRepository notivicationTaskRepository;

    public SheduleService(NotivicationTaskRepository notivicationTaskRepository) {
        this.notivicationTaskRepository = notivicationTaskRepository;
    }

    public Collection<NotificationTask> checkThisTime() {
        logger.info("*** Запущен метод checkThisTime() ***");
        logger.info("Time is " + LocalDateTime.now().toString());
        // ***** сравниваем текущее время с записями в БД, *******
        // *****  и возвращаем все записи из БД со временем, равным текущему *******
        Collection<NotificationTask> tasks = notivicationTaskRepository.findAllByLocalDateTime();

        logger.info(tasks.toString());
        logger.info("Nowtime tasks are: " + tasks);
        return tasks;
    }
}
