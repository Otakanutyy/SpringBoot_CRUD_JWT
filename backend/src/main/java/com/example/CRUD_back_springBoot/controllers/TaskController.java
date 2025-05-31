package com.example.CRUD_back_springBoot.controllers;

import com.example.CRUD_back_springBoot.models.Status;
import com.example.CRUD_back_springBoot.models.Task;
import com.example.CRUD_back_springBoot.models.User;
import com.example.CRUD_back_springBoot.services.TaskService;
import com.example.CRUD_back_springBoot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    // Helper to get User from authenticated email
    private User getCurrentUser(UserDetails userDetails) {
        return userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task, @AuthenticationPrincipal UserDetails userDetails) {
        User user = getCurrentUser(userDetails);
        task.setUser(user);
        Task created = taskService.createTask(task);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasksForUser(@AuthenticationPrincipal UserDetails userDetails) {
        User user = getCurrentUser(userDetails);
        List<Task> tasks = taskService.getTasksByUser(user);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Task>> getAllTasksForAdmin() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Status status) {
        User user = getCurrentUser(userDetails);
        List<Task> tasks = taskService.getTasksByUserAndStatus(user, status);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user = getCurrentUser(userDetails);
        Optional<Task> taskOpt = taskService.getTaskById(id);
        if (taskOpt.isPresent() && taskOpt.get().getUser().getId() == user.getId()) {
            return ResponseEntity.ok(taskOpt.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask, @AuthenticationPrincipal UserDetails userDetails) {
        User user = getCurrentUser(userDetails);
        Optional<Task> taskOpt = taskService.getTaskById(id);
        if (taskOpt.isPresent() && taskOpt.get().getUser().getId() == user.getId()) {
            Task task = taskOpt.get();
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setStatus(updatedTask.getStatus());
            Task saved = taskService.updateTask(task);
            return ResponseEntity.ok(saved);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user = getCurrentUser(userDetails);
        Optional<Task> taskOpt = taskService.getTaskById(id);
        if (taskOpt.isPresent() && taskOpt.get().getUser().getId() == user.getId()) {
            taskService.deleteTask(id);
            return ResponseEntity.ok("Task deleted");
        }
        return ResponseEntity.notFound().build();
    }
}
