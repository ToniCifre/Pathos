package cv.toni.pathos.service;

import cv.toni.pathos.model.Direccio;
import cv.toni.pathos.model.Role;
import cv.toni.pathos.model.User;
import cv.toni.pathos.repository.RoleRepository;
import cv.toni.pathos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

@Service("userService")
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(@Qualifier("userRepository") UserRepository userRepository,
                       @Qualifier("roleRepository") RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByName(String name) {
        return userRepository.findUserByName(name);
    }
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
    public User findUserById(int id){return userRepository.findUserById(id);}
    public List<User> findUsersByRole(Role role){return userRepository.findUsersByRoles(role);}

    public User createUser(User user, String Role) {
        try{
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            user.setActive(1);

            Role userRole = roleRepository.findByRole(Role);
            user.setRoles(new HashSet<>(Arrays.asList(userRole)));

            return userRepository.save(user);
        }catch (Exception e){
            System.out.println("---- ERROR CREATN UN USUARI --- ->" +user);
            System.err.println(e);
            return null;
        }
    }

    public User addDirection(Direccio d){
        System.out.println(d);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByEmail(auth.getName());
        Set<Direccio> aux = user.getDireccions();
        aux.add(d);
        user.setDireccions(aux);
        return userRepository.save(user);
    }

    public List<Direccio> getUserDirection(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByEmail(auth.getName());
        return new ArrayList<>(user.getDireccions());
    }

}