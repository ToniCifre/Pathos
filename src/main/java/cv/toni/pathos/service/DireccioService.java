package cv.toni.pathos.service;

import cv.toni.pathos.model.Direccio;
import cv.toni.pathos.model.User;
import cv.toni.pathos.repository.DireccioRepository;
import cv.toni.pathos.repository.MissatgeRepository;
import cv.toni.pathos.repository.NotificacioRepository;
import cv.toni.pathos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service("direccioService")
public class DireccioService {

    private DireccioRepository direccioRepository;
    private NotificacioRepository notificacioRepository;
    private UserRepository  userRepository;


    @Autowired
    public DireccioService(@Qualifier("direccioRepository") DireccioRepository direccioRepository,
                           @Qualifier("notificacioRepository") NotificacioRepository notificacioRepository,
                           @Qualifier("userRepository") UserRepository userRepository) {
        this.direccioRepository = direccioRepository;
        this.notificacioRepository = notificacioRepository;
        this.userRepository = userRepository;
    }

    public List<Direccio> findDirecciosByUserEmail(String uEmail){ return direccioRepository.findDirecciosByUserEmail(uEmail);}

    public Direccio findDireccio(int id) {
        try {
            return direccioRepository.findById(id);
        }catch (Exception e){return null;}
    }

    public Direccio saveDireccio(Direccio direccio, String email) {
        direccio.setUser(userRepository.findUserByEmail(email));
        return direccioRepository.save(direccio);
    }

    @Transactional
    public List<Direccio> savedirections(List<Direccio> d){
        return direccioRepository.saveAll(d);
    }

    public Direccio updateDireccio(Direccio d){
        Direccio direct = findDireccio(d.getId());
        direct.setDireccio(d.getDireccio());
        direct.setCp(d.getCp());
        return direccioRepository.save(direct);
    }

    public void delete(int id){
        Direccio d = findDireccio(id);
        if(d != null) {
            notificacioRepository.removeAllByDireccio(d);
            direccioRepository.delete(d);
        }
    }
}