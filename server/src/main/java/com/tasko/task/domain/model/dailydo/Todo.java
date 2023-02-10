package com.tasko.task.domain.model.dailydo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userId;

    @Column
    private String content;

    @Column
    private Boolean isDone;


    @Builder
    public Todo(String userId, String content, Boolean isDone) {
        this.userId = userId;
        this.content = content;
        this.isDone = isDone;
    }

    public void update(String content, Boolean isDone) {
        this.content = content;
        this.isDone = isDone;
    }
}
