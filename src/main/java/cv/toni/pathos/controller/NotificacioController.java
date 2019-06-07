package cv.toni.pathos.controller;

import cv.toni.pathos.model.Direccio;
import cv.toni.pathos.model.Notificacio;
import cv.toni.pathos.model.NotifyStat;
import cv.toni.pathos.model.User;
import cv.toni.pathos.service.NotificacioService;
import cv.toni.pathos.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Period;
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

        modelAndView.addObject("fragmentName", "notification");

        List<Notificacio> notificacions= notificationService.getRecivedNotifications();
        modelAndView.addObject("listNot", notificacions);

        modelAndView.setViewName("home");
        return modelAndView;
    }

    @PreAuthorize("hasRole('ORG')")
    @RequestMapping(value={"/notificacions/{tipus}"}, method = RequestMethod.GET)
    public ModelAndView notificationPagetype(@PathVariable("tipus") String estat){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        modelAndView.addObject("fragmentName", "notification");

        NotifyStat nt;
        switch (estat) {
            case "pendents":
                nt = NotifyStat.PENDENT;
                break;
            case "acteptat":
                nt = NotifyStat.ACCEPTAT;
                break;
            case "recollit":
                nt = NotifyStat.RECOLLIT;
                break;
            default:
                return new ModelAndView("redirect:/notificacions");
        }

        List<Notificacio> notificacions= notificationService.getRecivedNotificationsByEstat(nt);

        modelAndView.addObject("listNot", notificacions);

        return modelAndView;
    }

    @RequestMapping(value={"/enviar_notificacio/{org-id}"}, method = RequestMethod.GET)
    public ModelAndView createNotification( @PathVariable("org-id") int orgId){
        User user = userService.findUserById(orgId);
        if(user == null){
            return new ModelAndView("redirect:/");
        }else if(!user.getRole().getRole().equals("ORG")){
            return new ModelAndView("redirect:/");
        }

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("fragmentName", "createNotify");

        Notificacio notify = new Notificacio();
        modelAndView.addObject("notify", notify);

        List<Direccio> direccions = userService.getUserDirection();
        modelAndView.addObject("listDire", direccions);

        modelAndView.addObject("org", orgId);
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping(value = "/enviar_notificacio/{org-id}", method = RequestMethod.POST)
    public ModelAndView createNotification(@PathVariable("org-id") int orgId, @Valid Notificacio notificacio, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("redirect:/enviar_notificacio/"+orgId);
        }else{
            notificationService.createNotificacio(notificacio,orgId);
        }
        return new ModelAndView("redirect:/");

    }
}