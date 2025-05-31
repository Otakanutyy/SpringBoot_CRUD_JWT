package com.example.CRUD_back_springBoot.services;

import com.example.CRUD_back_springBoot.models.Status;
import com.example.CRUD_back_springBoot.models.Task;
import com.example.CRUD_back_springBoot.models.User;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task createTask(Task task);
    List<Task> getTasksByUser(User user);
    List<Task> getTasksByUserAndStatus(User user, Status status);
    Optional<Task> getTaskById(Long id);
    Task updateTask(Task task);
    List<Task> getAllTasks();
    void deleteTask(Long id);
}
