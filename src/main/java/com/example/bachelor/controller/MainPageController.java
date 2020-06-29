package com.example.bachelor.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainPageController {

    @GetMapping("/")
    public String getIndex(Model model)
    {
        model.addAttribute("eventName", "Fifa 2018");
        return "index";
    }
}
