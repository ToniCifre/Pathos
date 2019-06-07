package cv.toni.pathos.controller;

import cv.toni.pathos.extra.JavaFaker;
import cv.toni.pathos.model.User;
import cv.toni.pathos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value={"/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registration(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");

        if(bindingResult.hasFieldErrors()){
            return modelAndView;
        } else {
            if (user.getEmail().length() > 50) {
                bindingResult.rejectValue("email", "error.user",
                        "El email com a maxim ha de ser de 50 caracters");
            }
            if (user.getName().length() > 15) {
                bindingResult.rejectValue("name", "error.name",
                        "El nom com a maxim ha de ser de 15 caracters");
            }
            if (bindingResult.hasFieldErrors()) { return modelAndView; }

            User emailExists = userService.findUserByEmail(user.getEmail());
            if (emailExists != null) {
                bindingResult.rejectValue("email", "error.user", "Ja hi ha un usuari amb aquest email");
            }
            User nameExists = userService.findUserByName(user.getName());
            if (nameExists != null) {
                bindingResult.rejectValue("name", "error.user", "Ja hi ha un usuari amb aquest nom");
            }
            if (bindingResult.hasFieldErrors()) { return modelAndView; }

            else if (userService.createUser(user, "ORG",1) == null) {
                bindingResult.rejectValue("id", "error.user", "No sha pogut crear l'usuari");
            } else {
                modelAndView.setViewName("redirect:/login");
            }
        }
        return modelAndView;
    }
}
