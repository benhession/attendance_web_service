package com.benhession.attendance_web_service.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/home", produces = "text/plain")
@CrossOrigin("*")
public class HomeController {

    @GetMapping
    public String greeting() {
        return "Hello World!";
    }
}
