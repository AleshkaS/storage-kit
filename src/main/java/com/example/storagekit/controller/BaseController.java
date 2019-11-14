package com.example.storagekit.controller;

import com.example.storagekit.Utils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class BaseController {

    @GetMapping
    public ResponseEntity getInfo() {
        return ResponseEntity.ok(Utils.getMessage("Java service"));
    }
}
