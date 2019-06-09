package cv.toni.pathos.service;

import cv.toni.pathos.model.*;
import cv.toni.pathos.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.*;

@Service("userService")
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private DireccioRepository direccioRepository;
    private SalaRepository salaRepository;
    private NotificacioRepository notificacioRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(@Qualifier("userRepository") UserRepository userRepository,
                       @Qualifier("roleRepository") RoleRepository roleRepository,
                       @Qualifier("direccioRepository") DireccioRepository direccioRepository,
                       @Qualifier("salaRepository") SalaRepository salaRepository,
                       @Qualifier("notificacioRepository") NotificacioRepository notificacioRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.direccioRepository = direccioRepository;
        this.salaRepository = salaRepository;
        this.notificacioRepository = notificacioRepository;
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
    public User getMuOrg(User u) { return userRepository.findUserByColaboradorsContains(u); }

    public List<User> findUsersByRol(String role){return userRepository.findUsersByRole_Role(role);}

    public Role getRole(String role){
        return roleRepository.findByRole(role);
    }

    public List<Direccio> getUserDirection(User u){ return new ArrayList<>(direccioRepository.findDirecciosByUser(u)); }

    public User createUser(User user, String Role, int active){
        try{
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            user.setActive(active);

            Role userRole = roleRepository.findByRole(Role);
            user.setRole(userRole);

            user.setColaboradors(new ArrayList<>());

            return userRepository.save(user);

        }catch (Exception e){
            System.out.println("============= ERROR CREANT L'USUARI =============\n"+e);
            return null;
        }
    }

    public User createColaborador(User user, String Role, int active){
        try{
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setActive(active);
            Role userRole = roleRepository.findByRole(Role);
            user.setRole(userRole);

            User u = getUserAuth();

            u.addColaborador(u);
            u.setColaboradors(new ArrayList<>());

            return userRepository.save(user);

        }catch (Exception e){
            System.out.println("============= ERROR CREANT COLABORADOR =============\n"+e);
            return null;
        }
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

    @Transactional
    public void deleteUser(User user){
        try {
            List<Sala> salas;
            if(user.getRole().getRole().equals("ORG")){
                salas = salaRepository.findSalasBySalaId_OrgId_Email(user.getEmail());
                notificacioRepository.deleteAllByReceptor(user);
                if(!user.getColaboradors().isEmpty()){
                    userRepository.deleteAll(user.getColaboradors());
                }
            }else if(user.getRole().getRole().equals("COL")){
                salas = salaRepository.findSalasBySalaId_PersonaId_Email(user.getEmail());
                List<Notificacio> n = notificacioRepository.findAllByRecollidor(user);
                for (Notificacio nn: n) {
                    nn.setRecollidor(null);
                }
                notificacioRepository.saveAll(n);
            }else {
                salas = salaRepository.findSalasBySalaId_PersonaId_Email(user.getEmail());
                notificacioRepository.deleteAllByEmisor(user);
            }
            salaRepository.deleteAll(salas);

        }catch (Exception e){System.out.println("==================ERROR ELIMINST ELL USERS ==============");}
        userRepository.delete(user);
    }


    @Transactional
    public List<User> saveUsers(List<User> users){
        for (User u :users) {
            u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
            for (User c :u.getColaboradors()) {
                c.setPassword(bCryptPasswordEncoder.encode(c.getPassword()));
            }
        }
        return userRepository.saveAll(users);
    }
}