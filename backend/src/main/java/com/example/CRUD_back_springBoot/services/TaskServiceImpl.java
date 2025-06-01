package com.example.CRUD_back_springBoot.services;

import com.example.CRUD_back_springBoot.DTOs.TaskRequest;
import com.example.CRUD_back_springBoot.models.Status;
import com.example.CRUD_back_springBoot.models.Task;
import com.example.CRUD_back_springBoot.models.User;
import com.example.CRUD_back_springBoot.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Transactional
    @Override
    public Task createTask(User user, TaskRequest taskRequest) {
        Task task = new Task();
        task.setUser(user);
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getTasksByUser(User user) {
        return taskRepository.findByUser(user);
    }

    @Override
    public List<Task> getTasksByUserAndStatus(User user, Status status) {
        return taskRepository.findByUserAndStatus(user, status);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Transactional
    @Override
    public Task updateTask(Long id, TaskRequest dto) {
        Task task = taskRepository.findById(id).orElseThrow();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        if(dto.getStatus() != null) {
            task.setStatus(dto.getStatus());
        }
        return task;
    }

    @Transactional
    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public boolean isOwner(Task task, User user) {
        return task.getUser().equals(user);
    }

    public Optional<Status> tryParseStatus(String statusString) {
        try {
            return Optional.of(Status.valueOf(statusString.trim().toUpperCase()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
