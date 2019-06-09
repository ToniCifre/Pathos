package cv.toni.pathos.service;

import cv.toni.pathos.model.Notificacio;
import cv.toni.pathos.model.NotifyStat;
import cv.toni.pathos.model.User;
import cv.toni.pathos.repository.DireccioRepository;
import cv.toni.pathos.repository.NotificacioRepository;
import cv.toni.pathos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service("notificacioService")
public class NotificacioService {

    private NotificacioRepository notificacioRepository;
    private UserRepository userRepository;
    private DireccioRepository direccioRepository;

    @Autowired
    public NotificacioService(@Qualifier("notificacioRepository") NotificacioRepository notificacioRepository,
                              @Qualifier("userRepository") UserRepository userRepository,
                              @Qualifier("direccioRepository") DireccioRepository direccioRepository) {
        this.notificacioRepository = notificacioRepository;
        this.userRepository = userRepository;
        this.direccioRepository = direccioRepository;
    }

    public List<Notificacio> getRecivedNotifications(User u){
        return notificacioRepository.findNotificaciosByReceptor_EmailOrderByDataDesc(u.getEmail());
    }
    public List<Notificacio> getOrgRecivedNotifications(User u){
        return notificacioRepository.findNotificaciosByReceptor_EmailOrderByDataDesc(u.getEmail());
    }
    public List<Notificacio> getSendedNotifications(User user){
        try { return notificacioRepository.findAllByEmisor_IdOrderByDataDesc(user.getId());
        }catch (Exception e ){return new ArrayList<>();}
    }

    public List<Notificacio> getRecivedNotificationsByEstat(User u,NotifyStat stat){
        return notificacioRepository.findAllByReceptor_EmailAndEstatOrderByDataDesc( u.getEmail(), stat);
    }

    public Notificacio findNotificaciosById(int id){return notificacioRepository.findNotificaciosById(id);}

    public Notificacio createNotificacio(Notificacio n, int receptorId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        n.setEmisor(userRepository.findUserByEmail(auth.getName()));

        n.setReceptor(userRepository.findUserById(receptorId));

        n.setDireccio(direccioRepository.findById(n.getId_direccio()));

        n.setData(LocalDateTime.now());

        n.setEstat(NotifyStat.PENDENT);

        return notificacioRepository.save(n);
    }

    public Notificacio setEstat(String estat, int id, User u){
        Notificacio n = findNotificaciosById(id);
        n.setEstat(NotifyStat.valueOf(estat));
        n.setRecollidor(u);
        return notificacioRepository.save(n);
    }



    public List<Notificacio> saveNotifications(List<Notificacio> n){
        return notificacioRepository.saveAll(n);
    }



    public void delete(int id){
        notificacioRepository.delete(findNotificaciosById(id));
    }
}
