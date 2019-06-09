package cv.toni.pathos.controller;

import cv.toni.pathos.model.Direccio;
import cv.toni.pathos.model.Missatge;
import cv.toni.pathos.model.Notificacio;
import cv.toni.pathos.model.User;
import cv.toni.pathos.service.DireccioService;
import cv.toni.pathos.service.MissatgeService;
import cv.toni.pathos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class DireccioController {

    @Autowired
    private DireccioService direccioService;
    @Autowired
    private UserService userService;
    @Autowired
    private MissatgeService missatgeService;

    @RequestMapping(value={"/createDireccio"}, method = RequestMethod.GET)
    public ModelAndView createDirection(Principal principal){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home.html");
        User auth = userService.getUserAuth();
        modelAndView.addObject("auth", auth);
        int nMis = missatgeService.getcountMsg();
        modelAndView.addObject("nMis", nMis);
        List<Missatge> msnList = missatgeService.find5Missatger();
        modelAndView.addObject("msnList", msnList);

        modelAndView.addObject("fragmentName", "createDireccio");

        Direccio direccio = new Direccio();
        modelAndView.addObject("direccio", direccio);

        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping(value = "/createDireccio", method = RequestMethod.POST)
    public ModelAndView createDirection(@Valid Direccio direccio, BindingResult bindingResult, Principal principal){
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("home.html");
            User auth = userService.getUserAuth();
            modelAndView.addObject("auth", auth);
            int nMis = missatgeService.getcountMsg();
            modelAndView.addObject("nMis", nMis);
            List<Missatge> msnList = missatgeService.find5Missatger();
            modelAndView.addObject("msnList", msnList);
            modelAndView.addObject("fragmentName", "createDireccio");

            modelAndView.addObject("direccio", direccio);

            modelAndView.setViewName("home");
            return modelAndView;
        }

        direccioService.saveDireccio(direccio, principal.getName());

        return new ModelAndView("redirect:/adminDireccio");
    }


    @RequestMapping(value={"/adminDireccio"}, method = RequestMethod.GET)
    public ModelAndView adminDirection(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home.html");
        User auth = userService.getUserAuth();
        modelAndView.addObject("auth", auth);
        int nMis = missatgeService.getcountMsg();
        modelAndView.addObject("nMis", nMis);
        List<Missatge> msnList = missatgeService.find5Missatger();
        modelAndView.addObject("msnList", msnList);
        modelAndView.addObject("fragmentName", "adminDireccio");

        List<Direccio> listDirect = direccioService.findDirecciosByUser(auth);
        modelAndView.addObject("listDirect", listDirect);
        modelAndView.addObject("direccio", new Direccio());

        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping(value={"/adminDireccio/{dir-id}"}, method = RequestMethod.POST)
    public ModelAndView updateDireccio(@PathVariable("dir-id") int dirId, @Valid Direccio direccio){
        direccio.setId(dirId);
        System.out.println(dirId);
        direccioService.updateDireccio(direccio);
        return new ModelAndView("redirect:/adminDireccio");
    }


    @GetMapping("/delete/direccio/{id}")
    public ModelAndView deleteUser(@PathVariable("id") int id) {
       direccioService.delete(id);
        return new ModelAndView("redirect:/");
    }
}
