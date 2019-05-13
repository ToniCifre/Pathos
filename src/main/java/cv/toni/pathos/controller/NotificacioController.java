package cv.toni.pathos.controller;

import cv.toni.pathos.model.Notificacio;
import cv.toni.pathos.model.User;
import cv.toni.pathos.service.NotificacioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class NotificacioController {

    @Autowired
    NotificacioService notificationService;

//    @PreAuthorize("hasRole('ORG')")
    @RequestMapping(value={"/notificacions"}, method = RequestMethod.GET)
    public ModelAndView notificationPage(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.addObject("userName", auth.getName());

        modelAndView.addObject("fragmentName", "notification");

        List<Notificacio> notificacions= notificationService.getReciveNotifications();
        modelAndView.addObject("listNot", notificacions);



        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping(value={"/createNotify"}, method = RequestMethod.GET)
    public ModelAndView createNotification(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.addObject("userName", auth.getName());

        modelAndView.addObject("fragmentName", "createNotify");

        Notificacio notify = new Notificacio();
        modelAndView.addObject("notify", notify);


        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping(value = "/createNotify", method = RequestMethod.POST)
    public ModelAndView createNotification(@Valid Notificacio notificacio, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();


        return modelAndView;
    }
}