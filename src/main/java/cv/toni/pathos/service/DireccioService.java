package cv.toni.pathos.service;

import cv.toni.pathos.model.Direccio;
import cv.toni.pathos.model.User;
import cv.toni.pathos.repository.DireccioRepository;
import cv.toni.pathos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service("direccioService")
public class DireccioService {

    private UserRepository userRepository;
    private DireccioRepository direccioRepository;

    @Autowired
    public DireccioService(@Qualifier("direccioRepository") DireccioRepository direccioRepository,
                           @Qualifier("userRepository") UserRepository userRepository) {
        this.direccioRepository = direccioRepository;
        this.userRepository = userRepository;
    }

    public Direccio findByCp(int cp) {
        return direccioRepository.findByCp(cp);
    }
    public List<Direccio> findAllByCarrerAndNum(String carrer, int num) {
        return direccioRepository.findAllByCarrerAndNum(carrer, num);
    }

    public Direccio saveDireccio(Direccio direccio) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        direccio.setUsers(new HashSet<User>(Arrays.asList(user)));
        return direccioRepository.save(direccio);
    }
}