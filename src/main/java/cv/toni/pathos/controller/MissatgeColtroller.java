package cv.toni.pathos.controller;

import cv.toni.pathos.model.Missatge;
import cv.toni.pathos.model.Sala;
import cv.toni.pathos.model.User;
import cv.toni.pathos.service.MissatgeService;
import cv.toni.pathos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MissatgeColtroller {
    @Autowired
    private UserService userService;
    @Autowired
    private MissatgeService missatgeService;


    @RequestMapping(value={"/missatges"}, method = RequestMethod.GET)
    public ModelAndView missatgePage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        User user = userService.getUserAuth();
        modelAndView.addObject("name", user.getName());
        modelAndView.addObject("logo", user.getPhoto());
        modelAndView.addObject("fragmentName", "missatges");
        List<Missatge> msnList = missatgeService.find5Missatger();
        modelAndView.addObject("msnList", msnList);

        List<Sala> salaList = missatgeService.findAllSalas();
        List<User> listContact = salaList.stream().map(sala -> sala.getSalaId().getPersonaId()).collect(Collectors.toList());
        modelAndView.addObject("listContact", listContact);


        return modelAndView;
    }

    @RequestMapping(value={"/missatges/{contact}"}, method = RequestMethod.GET)
    public ModelAndView missatgePageContact(@PathVariable("contact") int contact){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        User user = userService.getUserAuth();
        modelAndView.addObject("name", user.getName());
        modelAndView.addObject("logo", user.getPhoto());
        modelAndView.addObject("fragmentName", "missatges");
        List<Missatge> msnLists = missatgeService.find5Missatger();
        modelAndView.addObject("msnList", msnLists);

        List<Sala> salaList = missatgeService.findAllSalas();
        List<User> listContact = salaList.stream().map(sala -> sala.getSalaId().getPersonaId()).collect(Collectors.toList());
        modelAndView.addObject("listContact", listContact);

        Sala s = missatgeService.findSala(contact);
        List<Missatge> msnList = missatgeService.findAllBySala(s);
        modelAndView.addObject("listMsn", msnList);

        modelAndView.addObject("contacte", s.getSalaId().getPersonaId());

        modelAndView.addObject("missatge",new Missatge());

        return modelAndView;
    }


    @RequestMapping(value={"/enviarMissatge/{contact}"}, method = RequestMethod.POST)
    public ModelAndView sendMissatge(@PathVariable("contact") int contact, @Valid Missatge missatge, BindingResult bindingResult){

        missatgeService.createMissatge(missatge, contact);

        return new ModelAndView("redirect:/missatges/"+contact);
    }



}
