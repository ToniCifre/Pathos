package cv.toni.pathos.controller;

import cv.toni.pathos.model.Missatge;
import cv.toni.pathos.model.Notificacio;
import cv.toni.pathos.model.NotifyStat;
import cv.toni.pathos.model.User;
import cv.toni.pathos.service.MissatgeService;
import cv.toni.pathos.service.NotificacioService;
import cv.toni.pathos.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class NotificacioController {

    @Autowired
    private NotificacioService notificationService;
    @Autowired
    private UserService userService;
    @Autowired
    private MissatgeService missatgeService;

    @PreAuthorize("hasRole('ORG')")
    @RequestMapping(value={"/notificacions"}, method = RequestMethod.GET)
    public ModelAndView notificationPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        int nMis = missatgeService.getcountMsg();
        modelAndView.addObject("nMis", nMis);
        User user = userService.getUserAuth();
        modelAndView.addObject("name", user.getName());
        modelAndView.addObject("logo", user.getPhoto());
        modelAndView.addObject("fragmentName", "notification");
        List<Missatge> msnList = missatgeService.find5Missatger();
        modelAndView.addObject("msnList", msnList);

        List<Notificacio> notificacions = notificationService.getRecivedNotifications();
        modelAndView.addObject("listNot", notificacions);

        return modelAndView;
    }

    @PreAuthorize("hasRole('ORG')")
    @RequestMapping(value={"/notificacions/{tipus}"}, method = RequestMethod.GET)
    public ModelAndView notificationPagetype(@PathVariable("tipus") String estat){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        int nMis = missatgeService.getcountMsg();
        modelAndView.addObject("nMis", nMis);
        User user = userService.getUserAuth();
        modelAndView.addObject("name", user.getName());
        modelAndView.addObject("logo", user.getPhoto());
        modelAndView.addObject("fragmentName", "notification");
        List<Missatge> msnList = missatgeService.find5Missatger();
        modelAndView.addObject("msnList", msnList);

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

    @RequestMapping(value={"/notificacions/enviades"}, method = RequestMethod.GET)
    public ModelAndView notificationEnviadesPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        int nMis = missatgeService.getcountMsg();
        modelAndView.addObject("nMis", nMis);
        User user = userService.getUserAuth();
        modelAndView.addObject("name", user.getName());
        modelAndView.addObject("logo", user.getPhoto());
        modelAndView.addObject("fragmentName", "notification");
        List<Missatge> msnList = missatgeService.find5Missatger();
        modelAndView.addObject("msnList", msnList);

        List<Notificacio> notificacions= notificationService.getSendedNotifications();
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
        modelAndView.setViewName("home");
        user = userService.getUserAuth();
        int nMis = missatgeService.getcountMsg();
        modelAndView.addObject("nMis", nMis);
        modelAndView.addObject("name", user.getName());
        modelAndView.addObject("logo", user.getPhoto());
        modelAndView.addObject("fragmentName", "createNotify");
        List<Missatge> msnList = missatgeService.find5Missatger();
        modelAndView.addObject("msnList", msnList);

        modelAndView.addObject("notify", new Notificacio());

        modelAndView.addObject("listDire", userService.getUserDirection());

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


    @RequestMapping(value={"/setEstat/{id}/{estat}"}, method = RequestMethod.GET)
    public ModelAndView cambirEstatNotify( @PathVariable("id") int id,@PathVariable("estat") String estat){

        notificationService.setEstat(estat, id);
        return new ModelAndView("redirect:/notificacions");
    }

    @GetMapping("/delete/notificacio/{id}")
    public ModelAndView deleteUser(@PathVariable("id") int id) {
        notificationService.delete(id);
        return new ModelAndView("redirect:/");
    }
}