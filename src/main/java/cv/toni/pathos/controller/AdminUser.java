package cv.toni.pathos.controller;

import cv.toni.pathos.model.Missatge;
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
import java.util.List;


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
        modelAndView.setViewName("home.html");
        User user = userService.getUserAuth();
        modelAndView.addObject("auth", user);
        int nMis = missatgeService.getcountMsg();
        modelAndView.addObject("nMis", nMis);
        List<Missatge> msnList = missatgeService.find5Missatger();
        modelAndView.addObject("msnList", msnList);

        modelAndView.addObject("fragmentName", "nouColaborador");

        user = new User();
        user.setIsActive(false);
        modelAndView.addObject("user", user);

        return modelAndView;
    }

    @PreAuthorize("hasRole('ORG')")
    @RequestMapping(value={"/nouColaborador"}, method = RequestMethod.POST)
    public ModelAndView nowColaboradorPage(@Valid User user, BindingResult bindingResult){

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
                    if (userService.createColaborador(user, "COL", user.isIsActive()? 1 : 0) == null) {
                        bindingResult.rejectValue("id", "error.user", "No sha pogut crear El Colaborador");
                    } else {
                        return new ModelAndView("redirect:/adminColaborador");
                    }
                }
            }
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home.html");
        User auth = userService.getUserAuth();
        modelAndView.addObject("auth", auth);
        int nMis = missatgeService.getcountMsg();
        modelAndView.addObject("nMis", nMis);
        List<Missatge> msnList = missatgeService.find5Missatger();
        modelAndView.addObject("msnList", msnList);

        modelAndView.addObject("fragmentName", "nouColaborador");
        modelAndView.addObject("user", user);
        return modelAndView;
    }




    @GetMapping("/delete/{id}")
    public ModelAndView deleteUser(@PathVariable("id") int id) {
       /* User user = userService.findUserByEmail(principal.getName());
        if(user.getId() != id){
            return new ModelAndView("redirect:/");
        }*/
        userService.deleteUser(userService.findUserById(id));
        return new ModelAndView("redirect:/login");
    }



    @RequestMapping(value={"/adminUser"}, method = RequestMethod.GET)
    public ModelAndView adminUserPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home.html");
        User auth = userService.getUserAuth();
        modelAndView.addObject("auth", auth);
        int nMis = missatgeService.getcountMsg();
        modelAndView.addObject("nMis", nMis);
        List<Missatge> msnList = missatgeService.find5Missatger();
        modelAndView.addObject("msnList", msnList);

        modelAndView.addObject("fragmentName", "adminUser");

        modelAndView.addObject("user", auth);

        return modelAndView;
    }

    @RequestMapping(value={"/adminUser"}, method = RequestMethod.POST)
    public ModelAndView adminUserPagepost(User user){
        User u = userService.getUserAuth();
        u.setName(user.getName());
        u.setEmail(user.getEmail());
        u.setDescripcio(user.getDescripcio());
        userService.updateUser(u);
        return new ModelAndView("redirect:/adminUser");
    }

    @RequestMapping(value={"/adminContra"}, method = RequestMethod.POST)
    public ModelAndView admincontraPage(User user){
        User u = userService.getUserAuth();
        u.setPassword(user.getPassword());
        userService.updatePasword(u);
        return new ModelAndView("redirect:/adminUser");
    }

    @RequestMapping(value={"/adminColaborador"}, method = RequestMethod.GET)
    public ModelAndView adminColPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home.html");
        User auth = userService.getUserAuth();
        modelAndView.addObject("auth", auth);
        int nMis = missatgeService.getcountMsg();
        modelAndView.addObject("nMis", nMis);
        List<Missatge> msnList = missatgeService.find5Missatger();
        modelAndView.addObject("msnList", msnList);

        modelAndView.addObject("fragmentName", "AdminCol");


        List<User> colList = userService.getUserAuth().getColaboradors();
        modelAndView.addObject("colList", colList);

        return modelAndView;
    }

    @RequestMapping(value={"/adminColaborador/{col-id}"}, method = RequestMethod.GET)
    public ModelAndView adminColPage(@PathVariable("col-id") int colId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home.html");
        User auth = userService.getUserAuth();
        modelAndView.addObject("auth", auth);
        int nMis = missatgeService.getcountMsg();
        modelAndView.addObject("nMis", nMis);
        List<Missatge> msnList = missatgeService.find5Missatger();
        modelAndView.addObject("msnList", msnList);

        modelAndView.addObject("fragmentName", "nouColaborador");

        User col = userService.findUserById(colId);
        col.setIsActive(col.getActive() == 1);
        modelAndView.addObject("user", col);

        return modelAndView;
    }

    @RequestMapping(value={"/adminColaborador/{col-id}"}, method = RequestMethod.POST)
    public ModelAndView adminColeditPage(@PathVariable("col-id") int colId, @Valid User user, BindingResult bindingResult) {
        User col = userService.findUserById(colId);
        col.setEmail(user.getEmail());
        col.setName(user.getName());
        col.setActive(user.isIsActive() ? 1:0);
        userService.updateUser(col);
        return new ModelAndView("redirect:/adminColaborador");

    }
}
