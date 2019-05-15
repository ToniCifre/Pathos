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

    private DireccioRepository direccioRepository;

    @Autowired
    public DireccioService(@Qualifier("direccioRepository") DireccioRepository direccioRepository) {
        this.direccioRepository = direccioRepository;
    }


    public Direccio saveDireccio(Direccio direccio) {
        return direccioRepository.save(direccio);
    }
}