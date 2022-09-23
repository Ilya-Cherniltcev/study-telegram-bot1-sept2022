package pro.sky.telegrambot.services;

import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repositories.NotivicationTaskRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

//@Component
@Service
public class MessagesService {
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final NotivicationTaskRepository notivicationTaskRepository;

    public MessagesService(NotivicationTaskRepository notivicationTaskRepository) {
        this.notivicationTaskRepository = notivicationTaskRepository;
    }

    // --------- Говорим Hello пользователю -------------------------------------------
    public String sayHelloToUser(Update update) {
        String user = update.message().chat().firstName()
                != null ? update.message().chat().firstName() : "user";
        String messageText = "Привет " + user +
                "! Я учебный бот-напоминальщик. Чтобы установить напоминание, введите: ДАТА | ВРЕМЯ | ТЕКСТ НАПОМИНАНИЯ " +
                "в следующем формате  dd.MM.yyyy HH:mm ТЕКСТ (Пример   14.02.2022 14:00 Поздравить друзей с Днем Св.Валетина)";
        return messageText;
    }

    // --------- Говорим "Ошибка" пользователю -------------------------------------------
    public String sayAboutMistakeToUser() {
        String messageText = "Некорректный ввод. " +
                "Для установки напоминания, введите: ДАТА | ВРЕМЯ | ТЕКСТ НАПОМИНАНИЯ " +
                "в формате  dd.MM.yyyy HH:mm ТЕКСТ     Например, 31.12.2022 23:55 Загадать желание :)";
        return messageText;
    }

    // *****************************************************************************************
    public String checkInputMessage(Long chatId, String userMessage) {
        //  --- (1) if userMessage == "/start" -------------------------
        if (userMessage.equals("/start")) {
            return "start";
        }
        // ---- (2) ---- Задаем формат паттерна и разделяем вводимое сообщение на 3 части
        // ---- (разделитель - пробел \\s) Парсим )) ---------------------
        String date = "";
        String time = "";
        String note = "";
        try {
            Pattern pattern = Pattern.compile("\\s");
            String[] strings = pattern.split(userMessage, 3);
            date = strings[0];
            time = strings[1];
            note = strings[2];
        } catch (IndexOutOfBoundsException e) {
            logger.info("Incorrect numbers of parts of array");
            return "Incorrect";
        }

        // ---- (3) ---- Проверяем на соответствие даты и времени -----
        String pattern = "dd.MM.yyyy HH:mm";
        String dateTime = date + " " + time;
        LocalDateTime localDateTime;
        try {
            localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(pattern));
        } catch (Exception e) {
            logger.info(date + " - Incorrect format of date/time");
            return "Incorrect";
        }

        logger.info("Date is " + date);
        logger.info("Time is " + time);
        logger.info("Note is " + note);

        // ---- (4) ---- Всё ОК. Записываем значения в таблицу -------------------

        logger.info("LocalDateTime " + localDateTime);
        addInfoToBase(chatId, note, localDateTime);
        return "ok";
    }

    // ========== записываем информацию в таблицу ============
    private void addInfoToBase(Long idChat, String message, LocalDateTime dateTime) {
        logger.info("Записываем в БД: idChat {}, message- {}, dateTime {}", idChat, message, dateTime);
        NotificationTask notificationTask = new NotificationTask();
        notificationTask.setIdChat(idChat);
        notificationTask.setMessage(message);
        notificationTask.setDateTime(dateTime);
        notivicationTaskRepository.save(notificationTask);
    }

    // ========== удаляем запись из таблицы ============
    private void removeInfoToBase(Long idChat, String message, LocalDateTime dateTime) {
        logger.info("Записываем в БД: idChat {}, message- {}, dateTime {}", idChat, message, dateTime);
        NotificationTask notificationTask = new NotificationTask();
        notificationTask.setIdChat(idChat);
        notificationTask.setMessage(message);
        notificationTask.setDateTime(dateTime);
        notivicationTaskRepository.save(notificationTask);
    }
}
