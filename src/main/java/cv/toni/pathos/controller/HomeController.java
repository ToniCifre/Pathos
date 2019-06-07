package cv.toni.pathos.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomeController {

    @PreAuthorize("hasRole('ORG')")
    @RequestMapping(value={"/","/home"}, method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("fragmentName", "home");

        modelAndView.setViewName("home.html");

        return modelAndView;
    }
}
