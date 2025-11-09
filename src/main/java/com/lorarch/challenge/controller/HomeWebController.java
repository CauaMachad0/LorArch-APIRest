package com.lorarch.challenge.controller;

import com.lorarch.challenge.service.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeWebController {

    @Autowired
    private MotoService motoService;

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("stats", motoService.getEstatisticaGeral());
        return "index";
    }
}