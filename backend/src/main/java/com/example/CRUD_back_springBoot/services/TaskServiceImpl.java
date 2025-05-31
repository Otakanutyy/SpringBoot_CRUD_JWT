package com.example.CRUD_back_springBoot.services;

import com.example.CRUD_back_springBoot.models.Status;
import com.example.CRUD_back_springBoot.models.Task;
import com.example.CRUD_back_springBoot.models.User;
import com.example.CRUD_back_springBoot.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public Task createTask(Task task) {
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

    @Override
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }


}
