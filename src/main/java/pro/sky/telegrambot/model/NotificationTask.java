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
    private Long id_chat;
    private String message;
    private LocalDateTime date_time;

    public Integer getKey() {
        return key;
    }

    public Long getId_chat() {
        return id_chat;
    }

    public void setId_chat(Long id_chat) {
        this.id_chat = id_chat;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate_time() {
        return date_time;
    }

    public void setDate_time(LocalDateTime date_time) {
        this.date_time = date_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return Objects.equals(key, that.key) && Objects.equals(id_chat, that.id_chat) && Objects.equals(message, that.message) && Objects.equals(date_time, that.date_time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, id_chat, message, date_time);
    }

    @Override
    public String toString() {
        return "NotificationTask{" +
                "key=" + key +
                ", id_chat=" + id_chat +
                ", message='" + message + '\'' +
                ", date_time=" + date_time +
                '}';
    }
}
