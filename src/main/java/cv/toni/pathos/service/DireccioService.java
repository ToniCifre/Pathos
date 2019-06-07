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

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service("direccioService")
public class DireccioService {

    private DireccioRepository direccioRepository;
    private UserRepository  userRepository;


    @Autowired
    public DireccioService(@Qualifier("direccioRepository") DireccioRepository direccioRepository,
                           @Qualifier("userRepository") UserRepository userRepository) {
        this.direccioRepository = direccioRepository;
        this.userRepository = userRepository;
    }

    public List<Direccio> findDirecciosByUserEmail(String uEmail){ return direccioRepository.findDirecciosByUserEmail(uEmail);}

    public Direccio saveDireccio(Direccio direccio, String email) {
        direccio.setUser(userRepository.findUserByEmail(email));
        return direccioRepository.save(direccio);
    }

    @Transactional
    public List<Direccio> savedirections(List<Direccio> d){
        return direccioRepository.saveAll(d);
    }
}