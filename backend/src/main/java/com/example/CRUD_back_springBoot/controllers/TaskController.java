package com.example.CRUD_back_springBoot.controllers;

import com.example.CRUD_back_springBoot.DTOs.StatusRequest;
import com.example.CRUD_back_springBoot.DTOs.TaskRequest;
import com.example.CRUD_back_springBoot.exceptions.UserIsNotAdminException;
import com.example.CRUD_back_springBoot.exceptions.UserIsNotOwnerException;
import com.example.CRUD_back_springBoot.models.Status;
import com.example.CRUD_back_springBoot.models.Task;
import com.example.CRUD_back_springBoot.models.User;
import com.example.CRUD_back_springBoot.services.TaskService;
import com.example.CRUD_back_springBoot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestBody TaskRequest taskRequest, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getCurrentUser(userDetails);
        Task created = taskService.createTask(user, taskRequest);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/get_tasks")
    public ResponseEntity<List<Task>> getAllTasksForUser(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getCurrentUser(userDetails);
        List<Task> tasks = taskService.getTasksByUser(user);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/get_all_tasks_admin")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Task>> getAllTasksForAdmin(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getCurrentUser(userDetails);
        if (!userService.isAdmin(user)) {
            throw new UserIsNotAdminException("You are not admin");
        }
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/get_by_status_user/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Status status) {
        User user = userService.getCurrentUser(userDetails);
        List<Task> tasks = taskService.getTasksByUserAndStatus(user, status);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/get_task/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getCurrentUser(userDetails);
        Task task = getTaskIfOwnerOrThrow(id, user);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id,
                                           @RequestBody TaskRequest updatedTask,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getCurrentUser(userDetails);
        Task task = getTaskIfOwnerOrThrow(id, user);
        try {
            Task updated = taskService.updateTask(id, updatedTask);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/set_status/{id}")
    public ResponseEntity<Task> setTaskStatus(@PathVariable Long id, @RequestBody StatusRequest statusRequest, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getCurrentUser(userDetails);
        Task task = getTaskIfOwnerOrThrow(id, user);

        Status taskStatus = taskService.tryParseStatus(statusRequest.status)
                .orElseThrow(() -> new IllegalArgumentException("Invalid status: " + statusRequest.status));

        TaskRequest taskRequest = new TaskRequest(task.getTitle(), task.getDescription(), taskStatus);
        try {
            Task updated = taskService.updateTask(id, taskRequest);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getCurrentUser(userDetails);
        Task task = getTaskIfOwnerOrThrow(id, user);
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task deleted");
    }

    private Task getTaskIfOwnerOrThrow(Long taskId, User user) {
        Task task = taskService.getTaskById(taskId)
                .orElseThrow(() -> new NoSuchElementException("Task not found"));
        if (!taskService.isOwner(task, user)) {
            throw new UserIsNotOwnerException("You are not the owner of this task.");
        }
        return task;
    }
}
