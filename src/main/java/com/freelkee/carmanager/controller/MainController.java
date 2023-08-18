package com.freelkee.carmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String showStartPage() {
        return "start";
    }
    @GetMapping("/info")
    public String showInfoPage() {
        return "optional/info";
    }
    @GetMapping("/contacts")
    public String showContactsPage() {
        return "optional/contacts";
    }

}
