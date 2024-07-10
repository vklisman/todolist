package br.com.vklisman.todolist.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb-users")
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    public String username;
    private String name;
    public String password;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
