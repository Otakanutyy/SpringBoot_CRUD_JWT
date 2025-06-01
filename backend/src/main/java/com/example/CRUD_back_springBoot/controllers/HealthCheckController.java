package com.example.CRUD_back_springBoot.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Health", description = "Liveness probe for load-balancers / Kubernetes")
public class HealthCheckController {
    @Operation(
            summary     = "Health check",
            description = "Returns **200 OK** with plain-text `OK` when the service is alive.",
            responses   = {
                    @ApiResponse(responseCode = "200", description = "Service is up")
            }
    )
    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
