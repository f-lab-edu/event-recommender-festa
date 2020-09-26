package com.festa.controller;

import com.festa.dto.HostDTO;
import com.festa.service.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class HostController {
    @Autowired
    private HostService hostService;

}
