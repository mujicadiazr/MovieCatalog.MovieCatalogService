package com.mujicaservices.moviecatalogservice.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthResource {

    @GetMapping
    public String get(){
        return "Healthy!";
    }
}
