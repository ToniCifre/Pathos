package cv.toni.pathos.controller;

import cv.toni.pathos.model.User;
import cv.toni.pathos.service.MissatgeService;
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
import java.security.Principal;


@Controller
public class AdminUser {

    @Autowired
    private UserService userService;
    @Autowired
    private MissatgeService missatgeService;


    @PreAuthorize("hasRole('ORG')")
    @RequestMapping(value={"/nouColaborador"}, method = RequestMethod.GET)
    public ModelAndView nowColaboradorPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        User user = userService.getUserAuth();
        int nMis = missatgeService.getcountMsg();
        modelAndView.addObject("nMis", nMis);
        modelAndView.addObject("name", user.getName());
        modelAndView.addObject("logo", user.getPhoto());
        modelAndView.addObject("fragmentName", "nouColaborador");

        user = new User();
        modelAndView.addObject("user", user);

        return modelAndView;
    }

    @PreAuthorize("hasRole('ORG')")
    @RequestMapping(value={"/nouColaborador"}, method = RequestMethod.POST)
    public ModelAndView nowColaboradorPage(@Valid User user, BindingResult bindingResult, Principal principal){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        int nMis = missatgeService.getcountMsg();
        modelAndView.addObject("nMis", nMis);
        User u = userService.getUserAuth();
        modelAndView.addObject("name", u.getName());
        modelAndView.addObject("logo", u.getPhoto());
        modelAndView.addObject("fragmentName", "nouColaborador");

        if(!bindingResult.hasFieldErrors()){
            if (user.getEmail().length() > 50) {
                bindingResult.rejectValue("email", "error.user",
                        "El email com a maxim ha de ser de 50 caracters");
            }
            if (user.getName().length() > 15) {
                bindingResult.rejectValue("name", "error.name",
                        "El nom com a maxim ha de ser de 15 caracters");
            }
            if (!bindingResult.hasFieldErrors()) {
                User emailExists = userService.findUserByName(user.getName());
                User nameExists = userService.findUserByEmail(user.getEmail());
                if (emailExists != null) {
                    bindingResult.rejectValue("email", "error.user", "Ja hi ha un usuari amb aquest email");
                }
                if (nameExists != null) {
                    bindingResult.rejectValue("name", "error.user", "Ja hi ha un usuari amb aquest nom");
                }
                if (!bindingResult.hasFieldErrors()) {
                    user.setOrgId(u);
                    if (userService.createUser(user, "COL", user.isActive()? 1 : 0) == null) {
                        bindingResult.rejectValue("id", "error.user", "No sha pogut crear El Colaborador");
                    } else {
                        return new ModelAndView("redirect:/");
                    }
                }
            }
        }
        return modelAndView;
    }




    @GetMapping("/delete/{id}")
    public ModelAndView deleteUser(@PathVariable("id") int id, Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        if(user.getId() != id){
            return new ModelAndView("redirect:/");
        }
        userService.deleteUser(user);
        return new ModelAndView("redirect:/login");
    }


}
