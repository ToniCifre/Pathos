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
import java.security.Principal;

@Controller
public class DireccioController {

    @Autowired
    private DireccioService direccioService;

    @RequestMapping(value={"/createDireccio"}, method = RequestMethod.GET)
    public ModelAndView createDirection(Principal principal){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("fragmentName", "createDireccio");

        Direccio direccio = new Direccio();
        modelAndView.addObject("direccio", direccio);
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping(value = "/createDireccio", method = RequestMethod.POST)
    public ModelAndView createDirection(@Valid Direccio direccio, BindingResult bindingResult, Principal principal){
        if (bindingResult.hasErrors()) {
            return new ModelAndView("redirect:/createDireccio");
        }else{
            direccioService.saveDireccio(direccio, principal.getName());
        }
        return new ModelAndView("redirect:/");

    }
}
