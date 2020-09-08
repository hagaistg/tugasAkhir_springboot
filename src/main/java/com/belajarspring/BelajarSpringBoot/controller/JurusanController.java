package com.belajarspring.BelajarSpringBoot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class JurusanController {

    @RequestMapping(value = "/jurusan", method = RequestMethod.GET)
    public String getLoginForm(){
        return "jurusan";
    }

    @RequestMapping(value = "/jurusan/id/{id}", method = RequestMethod.GET)
    public String getJurusan(@PathVariable String id)
    {
        System.out.println("Key = " + id);
        return "detail_jurusan";

    }
}
