package pro.sky.telegrambot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.telegrambot.model.NotificationTask;

import java.util.Collection;

public interface NotivicationTaskRepository extends JpaRepository<NotificationTask, Integer> {

    // ===== Находим и возвращаем все записи из БД со временем, равным текущему =======
    @Query(value = "SELECT * FROM notification_task WHERE date_time BETWEEN DATE_TRUNC('minute', CURRENT_TIMESTAMP) " +
            "AND DATE_TRUNC('minute', CURRENT_TIMESTAMP) + (interval '59s')", nativeQuery = true)
    Collection<NotificationTask> findAllByLocalDateTime();

}
