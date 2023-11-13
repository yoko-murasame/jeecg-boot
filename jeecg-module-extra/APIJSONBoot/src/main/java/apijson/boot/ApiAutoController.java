package apijson.boot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ApiAutoController {

    @GetMapping(value = {"/"})
    public String index() {
        return "redirect:/index.html";
    }

}
