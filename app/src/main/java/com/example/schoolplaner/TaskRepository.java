package com.example.schoolplaner;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private static TaskRepository instance;
    private final List<Task> tasks;

    private TaskRepository() {
        tasks = new ArrayList<>();
    }

    public static TaskRepository getInstance() {
        if (instance == null) {
            instance = new TaskRepository();
        }
        return instance;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }
}
