package com.iv1201.project.recruitment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("msg", "hejsan");
        model.addAttribute("loggedIn", false);
        return "home";
    }

    @PostMapping("/")
    public String post(Model model) {
        model.addAttribute("msg", "svejsan");
        model.addAttribute("loggedIn", true);
        return "home";
    }

}
