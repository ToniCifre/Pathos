package cv.toni.pathos.service;

import cv.toni.pathos.model.Direccio;
import cv.toni.pathos.model.Role;
import cv.toni.pathos.model.User;
import cv.toni.pathos.repository.DireccioRepository;
import cv.toni.pathos.repository.RoleRepository;
import cv.toni.pathos.repository.SalaRepository;
import cv.toni.pathos.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("userService")
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private DireccioRepository direccioRepository;
    private SalaRepository salaRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(@Qualifier("userRepository") UserRepository userRepository,
                       @Qualifier("roleRepository") RoleRepository roleRepository,
                       @Qualifier("direccioRepository") DireccioRepository direccioRepository,
                       @Qualifier("salaRepository") SalaRepository salaRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.direccioRepository = direccioRepository;
        this.salaRepository = salaRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByName(String name) {
        return userRepository.findUserByName(name);
    }
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
    public User findUserById(int id) {
        try { return userRepository.findUserById(id); } catch (Exception e) { return null; }
    }
    public User getUserAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findUserByEmail(auth.getName());
    }
    public List<User> findUsersByRol(String role){return userRepository.findUsersByRole_Role(role);}

    public List<User> getColaboradors(String email){return userRepository.findUsersByOrgId_Email(email);}

    public User createUser(User user, String Role, int active){
        try{
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            user.setActive(active);

            Role userRole = roleRepository.findByRole(Role);
            user.setRole(userRole);

            return userRepository.save(user);

        }catch (Exception e){
            System.out.println("============= ERROR CREANT L'USUARI =============\n"+e);
            return null;
        }
    }

    @Transactional
    public List<User> saveUsers(List<User> users){
        for (User u :users) {
            u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
        }
        return userRepository.saveAll(users);
    }

    public User updateUser(User u){
        try {
            return userRepository.save(u);
        }catch (Exception e){return null;}
    }
    public User updatePasword(User u){
        u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
        return userRepository.save(u);
    }

    public Role getRole(String role){
        return roleRepository.findByRole(role);
    }

    public List<Direccio> getUserDirection(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return new ArrayList<>(direccioRepository.findDirecciosByUserEmail(auth.getName()));
    }



    public void deleteUser(User user){
        if(user.getRole().getRole().equals("ORG")){
            salaRepository.removeAllBySalaId_OrgId(user);
        }else{
            salaRepository.removeAllBySalaId_PersonaId(user);
        }
        userRepository.delete(user);
    }

}