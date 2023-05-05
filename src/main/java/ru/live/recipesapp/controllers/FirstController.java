package ru.live.recipesapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {

    @GetMapping("/")
    public String Start () {

        return "Приложение запущено";
    }
    @GetMapping("/info")
    public String Info () {
        return "Тукмачев Сергей " + " Recipes " + " 02.05.2023г " + " К проекту подключен Spring Framework и сборщик Maven, проект написан на языке программирования Java";
    }
}
