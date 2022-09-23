package pro.sky.telegrambot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class NotificationTask {
    @Id
    @GeneratedValue
    private Integer key;
    private Long idChat;
    private String message;
    private LocalDateTime dateTime;

    public Integer getKey() {
        return key;
    }

    public Long getIdChat() {
        return idChat;
    }

    public void setIdChat(Long idChat) {
        this.idChat = idChat;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return Objects.equals(key, that.key) && Objects.equals(idChat, that.idChat) && Objects.equals(message, that.message) && Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, idChat, message, dateTime);
    }

    @Override
    public String toString() {
        return "NotificationTask{" +
                "key=" + key +
                ", idChat=" + idChat +
                ", message='" + message + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
