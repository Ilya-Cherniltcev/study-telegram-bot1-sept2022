package pro.sky.telegrambot;

import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pro.sky.telegrambot.services.MessagesService;

import java.util.logging.Logger;

@SpringBootTest
public class MessagesServiceTest {
    private String DEFAULT_MESSAGE;
    private Update update;
    private Logger logger = new Logger();
    @Autowired
    MessagesService messagesService;

    @BeforeAll
    private void setup() {
        update = new Update();
        update.message().chat().firstName().replaceAll("", "ExampleUser");
        // --- set default Hello-message
        DEFAULT_MESSAGE = "������ ExampleUser! " +
                "� ������� ���-�������������. ����� ���������� �����������, �������: ���� | ����� | ����� ����������� \" +\n" +
                "                \"� ��������� �������  dd.MM.yyyy HH:mm ����� (������   14.02.2022 14:00 ���������� ������ � ���� ��.��������)";

    }

    @Test
    private void isWriteHelloMessage() {
        String actual = messagesService.sayHelloToUser(update);
        logger.info("actual = " + actual);
        Assertions.assertEquals(DEFAULT_MESSAGE, actual);
    }

//    public String sayHelloToUser(Update update) {
//        String user = update.message().chat().firstName()
//                != null ? update.message().chat().firstName() : "user";
//        String messageText = "������ " + user +
//                "! � ������� ���-�������������. ����� ���������� �����������, �������: ���� | ����� | ����� ����������� " +
//                "� ��������� �������  dd.MM.yyyy HH:mm ����� (������   14.02.2022 14:00 ���������� ������ � ���� ��.��������)";
//        return messageText;
//    }

}
