package com.pisto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/new")
public class NewController {

	@RequestMapping(method = RequestMethod.GET)
    public String sayNew(Model model) {
        model.addAttribute("new", "Hello World from Spring 4 MVC");
        return "new";
    }
}
