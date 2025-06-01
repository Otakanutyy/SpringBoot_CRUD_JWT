package com.example.CRUD_back_springBoot.DTOs;

import com.example.CRUD_back_springBoot.models.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {
    private String title;
    private String description;
    private Status status;
}
