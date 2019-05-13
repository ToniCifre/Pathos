package cv.toni.pathos.service;

import cv.toni.pathos.model.Notificacio;
import cv.toni.pathos.model.User;
import cv.toni.pathos.repository.NotificacioRepository;
import cv.toni.pathos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service("notificacioService")
public class NotificacioService {

    private NotificacioRepository notificacioRepository;
    private UserRepository userRepository;

    @Autowired
    public NotificacioService(@Qualifier("notificacioRepository") NotificacioRepository notificacioRepository,
                              @Qualifier("userRepository") UserRepository userRepository) {
        this.notificacioRepository = notificacioRepository;
        this.userRepository = userRepository;
    }

    public List<Notificacio> findNotificaciosByReceptorId(int idR){
        return notificacioRepository.findNotificaciosByReceptorId(idR);
    }
    public List<Notificacio> findNotificaciosByEmisorId(int idE){
        return notificacioRepository.findNotificaciosByEmisorId(idE);
    }
    public List<Notificacio> getReciveNotifications(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return notificacioRepository.findNotificaciosByReceptor_Email(authentication.getName());
    }
    public List<Notificacio> getSendedNotifications(User u){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        return notificacioRepository.findNotificaciosByEmisor(user);
    }

    public Notificacio createNotificacio(Notificacio n, int receptorId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        n.setEmisor(user);

        n.setData(LocalDateTime.now());

        user = userRepository.findUserById(receptorId);
        n.setReceptor(user);

        return notificacioRepository.save(n);
    }


}
