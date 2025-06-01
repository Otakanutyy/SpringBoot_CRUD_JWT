package com.example.CRUD_back_springBoot.services;

import com.example.CRUD_back_springBoot.DTOs.TaskRequest;
import com.example.CRUD_back_springBoot.models.Status;
import com.example.CRUD_back_springBoot.models.Task;
import com.example.CRUD_back_springBoot.models.User;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task createTask(User user, TaskRequest task);
    List<Task> getTasksByUser(User user);
    List<Task> getTasksByUserAndStatus(User user, Status status);
    Optional<Task> getTaskById(Long id);
    Task updateTask(Long id, TaskRequest taskRequest);
    List<Task> getAllTasks();
    void deleteTask(Long id);
    boolean isOwner(Task task, User user);
    Optional<Status> tryParseStatus(String statusString);
}
