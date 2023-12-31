package none.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

/*
THIS IS THE MODEL FOR THE TASK DATA BASE
*/

@Data
@Entity(name = "tb_tasks")
public class TaskModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String descriacao;

    @Column(length = 50)
    private String tittle;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String priority;

    private UUID idUser;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setTittle(String tittle) throws IllegalArgumentException {
        if (tittle.length() > 50) {
            throw new IllegalArgumentException("Tittle is too long");
        }
        this.tittle = tittle;
    }
}
