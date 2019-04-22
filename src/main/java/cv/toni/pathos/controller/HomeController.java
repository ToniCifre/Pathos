package cv.toni.pathos.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomeController {

    @RequestMapping(value={"/org/home", "/org"}, method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated() && auth.getAuthorities().stream().anyMatch(r ->
                r.getAuthority().equals("ORG") || r.getAuthority().equals("ADMIN") )) {
            modelAndView.addObject("userName", "-"+auth.getName()+"-");
            modelAndView.setViewName("org/home.html");
        }else{
            modelAndView.setViewName("access-denied");
        }

        return modelAndView;
    }
}
