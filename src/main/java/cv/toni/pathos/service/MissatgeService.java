package cv.toni.pathos.service;

import cv.toni.pathos.model.Missatge;
import cv.toni.pathos.model.User;
import cv.toni.pathos.repository.MissatgeRepository;
import cv.toni.pathos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("missatgeService")
public class MissatgeService {

    private MissatgeRepository missatgeRepository;
    private UserRepository userRepository;

    @Autowired
    public MissatgeService(@Qualifier("missatgeRepository") MissatgeRepository missatgeRepository,
                           @Qualifier("userRepository") UserRepository userRepository) {
        this.missatgeRepository = missatgeRepository;
        this.userRepository = userRepository;
    }

    public List<Missatge> getReciveMsg(User u){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return missatgeRepository.findAllByReceptor_EmailOrderByDataDesc(auth.getName());
    }
    public List<Missatge> getSendedMsg(User u){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return missatgeRepository.findAllByEmisor_EmailOrderByDataDesc(auth.getName());
    }
    public int getcountMsg(User u){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return missatgeRepository.countReceptor_EmailAndLlegitIsFalse(auth.getName());
    }


    public Missatge createMissatge(Missatge n, int receptorId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        n.setEmisor(userRepository.findUserByEmail(auth.getName()));

        n.setReceptor(userRepository.findUserById(receptorId));

        n.setLlegit(false);

        return missatgeRepository.save(n);
    }





    public List<Missatge> saveMissatges(List<Missatge> n){
        return missatgeRepository.saveAll(n);
    }
}
