package com.example.consumingrest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GreetingController {
    private static final String GREETING_ENDPOINT = "/greeting";

    @Value("${app.message}")
    private String message;

    @Autowired
    private GreetingService greetingService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping(GREETING_ENDPOINT)
    public String greeting(Model model) {
        model.addAttribute("message", message);
        model.addAttribute("names", greetingService.getNames());
        return "greeting";
    }

    @PostMapping(GREETING_ENDPOINT)
    public String addName(@RequestParam String name, Model model) {
        greetingService.addName(name);
        model.addAttribute("message", message);
        model.addAttribute("names", greetingService.getNames());
        return "greeting";
    }


}

@Service
class GreetingService {
    private final List<String> names = new ArrayList<>();

    public List<String> getNames() {
        return names;
    }

    public void addName(String name) {
        names.add(name);
    }
}

@RestController
class GreetingApiController {
    @Autowired
    private GreetingService greetingService;

    @GetMapping("/api/names")
    public List<String> getNames() {
        return greetingService.getNames();
    }

    @GetMapping("/api/random")
    public Quote getRandomQuote() {
        return new Quote("success", new Value(1L, "Esta es una cita de prueba"));
    }
}
