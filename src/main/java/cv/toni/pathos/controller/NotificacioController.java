package cv.toni.pathos.controller;

import cv.toni.pathos.model.Direccio;
import cv.toni.pathos.model.Notificacio;
import cv.toni.pathos.service.NotificacioService;
import cv.toni.pathos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class NotificacioController {

    @Autowired
    private NotificacioService notificationService;
    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ORG')")
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

    @RequestMapping(value={"/enviar_notificacio"}, method = RequestMethod.GET)
    public ModelAndView createNotification( @RequestParam("org-id") int orgId ){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        modelAndView.addObject("userName", auth.getName());
        modelAndView.addObject("fragmentName", "createNotify");

        Notificacio notify = new Notificacio();
        modelAndView.addObject("notify", notify);

        List<Direccio> direccions = userService.getUserDirection();
        modelAndView.addObject("listDire", direccions);

        modelAndView.addObject("org", orgId);
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping(value = "/enviar_notificacio", method = RequestMethod.POST)
    public ModelAndView createNotification(@RequestParam("org-id") int orgId, @Valid Notificacio notificacio, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.addObject("userName", auth.getName());

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("org", orgId);
            modelAndView.addObject("fragmentName", "createNotify");
            modelAndView.setViewName("home");
        }else{
            notificationService.createNotificacio(notificacio,orgId);
        }
        modelAndView.addObject("fragmentName", "home");
        modelAndView.setViewName("home");

        return modelAndView;
    }
}
