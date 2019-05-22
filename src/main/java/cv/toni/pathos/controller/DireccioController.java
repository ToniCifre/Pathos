package cv.toni.pathos.controller;

import cv.toni.pathos.model.Direccio;
import cv.toni.pathos.model.User;
import cv.toni.pathos.service.DireccioService;
import cv.toni.pathos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class DireccioController {

    @Autowired
    private DireccioService direccioService;
    @Autowired
    private UserService userService;

    @RequestMapping(value={"/createDireccio"}, method = RequestMethod.GET)
    public ModelAndView createDirection(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.addObject("userName", auth.getName());

        modelAndView.addObject("fragmentName", "createDireccio");

        Direccio direccio = new Direccio();
        modelAndView.addObject("direccio", direccio);
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping(value = "/createDireccio", method = RequestMethod.POST)
    public ModelAndView createDirection(@Valid Direccio direccio, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.addObject("userName", auth.getName());

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("fragmentName", "createDireccio");
            modelAndView.setViewName("home");
            return modelAndView;
        }else{
            try{
                direccio = direccioService.saveDireccio(direccio);
                System.out.println("direc- "+direccio);
                userService.addDirection(direccio);
            }catch (Exception e) {
                System.out.println(e);
                System.out.println("========== ERROR GUARDANT DIRECCIÓ ================");
            }

            modelAndView.addObject("fragmentName", "home");
            modelAndView.setViewName("home");
        }

        return modelAndView;
    }
}
