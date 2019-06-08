package cv.toni.pathos.controller;

import cv.toni.pathos.model.Missatge;
import cv.toni.pathos.model.User;
import cv.toni.pathos.service.MissatgeService;
import cv.toni.pathos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;



@Controller
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private MissatgeService missatgeService;

    @PreAuthorize("hasRole('ORG')")
    @RequestMapping(value={"/","/home"}, method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();

        User user = userService.getUserAuth();
        int nMis = missatgeService.getcountMsg();
        modelAndView.addObject("nMis", nMis);
        modelAndView.addObject("name", user.getName());
        modelAndView.addObject("logo", user.getPhoto());
        modelAndView.addObject("fragmentName", "home");
        List<Missatge> msnList = missatgeService.find5Missatger();
        modelAndView.addObject("msnList", msnList);

        modelAndView.setViewName("home.html");

        return modelAndView;
    }
}
