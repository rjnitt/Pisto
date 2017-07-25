package com.pisto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController {
 
    @RequestMapping(method = RequestMethod.GET)
    public String sayHello(Model model) {
        //model.addAttribute("greeting", "Hello World from Spring 4 MVC");
        return "hello";
    }
 
 
}