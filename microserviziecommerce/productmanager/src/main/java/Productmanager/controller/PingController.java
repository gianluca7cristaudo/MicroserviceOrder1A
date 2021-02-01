package Productmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/ping")
public class PingController {

    @GetMapping(path = "")
    public @ResponseBody
    String getPing() {
        return "{ \n" +
                "   serviceStatus: up \n" +
                "   dbStatus: up \n"  +
                '}';
    }
}
