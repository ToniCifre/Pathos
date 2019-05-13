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

    public List<Missatge> findMissatgesByReceptorId(int idR){
        return missatgeRepository.findMissatgesByReceptorId(idR);
    }
    public List<Missatge> findMissatgesByEmisorId(int idE){
        return missatgeRepository.findMissatgesByEmisorId(idE);
    }
    public List<Missatge> getReciveMsg(User u){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        return missatgeRepository.findMissatgesByReceptor(user);
    }
    public List<Missatge> getSendedMsg(User u){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        return missatgeRepository.findMissatgesByEmisor(user);
    }

    public Missatge createMissatge(Missatge n, int receptorId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        n.setEmisor(user);

        user = userRepository.findUserById(receptorId);
        n.setReceptor(user);

        return missatgeRepository.save(n);
    }


}
