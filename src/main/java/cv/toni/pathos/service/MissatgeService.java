package cv.toni.pathos.service;

import cv.toni.pathos.model.Missatge;
import cv.toni.pathos.model.Sala;
import cv.toni.pathos.model.SalaId;
import cv.toni.pathos.model.User;
import cv.toni.pathos.repository.MissatgeRepository;
import cv.toni.pathos.repository.SalaRepository;
import cv.toni.pathos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service("missatgeService")
public class MissatgeService {

    private MissatgeRepository missatgeRepository;
    private SalaRepository salaRepository;
    private UserRepository userRepository;

    @Autowired
    public MissatgeService(@Qualifier("missatgeRepository") MissatgeRepository missatgeRepository,
                           @Qualifier("salaRepository") SalaRepository salaRepository,
                           @Qualifier("userRepository") UserRepository userRepository) {
        this.missatgeRepository = missatgeRepository;
        this.salaRepository = salaRepository;
        this.userRepository = userRepository;
    }
    public int getcountMsg(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return missatgeRepository.countAllBySala_SalaId_OrgId_Email(auth.getName());
    }
    public List<Missatge> find5Missatger(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(userRepository.findUserByEmail(auth.getName()).getRole().getRole().equals("ORG")){
            System.out.println("dfjghskdfjlghjklsdfhgjklhsdfkljgsdfjklg");
            return missatgeRepository.findFirst5BySala_SalaId_OrgId_EmailAndSenderOrgTrueOrderByDataDesc(auth.getName());
        }else{
            System.out.println("nononoononononoonononono");
            return missatgeRepository.findFirst5BySala_SalaId_PersonaId_EmailAndSenderOrgTrueOrderByDataDesc(auth.getName());
        }
    }
    public List<Sala> findAllSalas(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(userRepository.findUserByEmail(auth.getName()).getRole().getRole().equals("ORG")){
            return salaRepository.findSalasBySalaId_OrgId_Email(auth.getName());
        }else{
            return salaRepository.findSalasBySalaId_PersonaId_Email(auth.getName());
        }
    }

    public Sala findSala(int id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByEmail(auth.getName());
        if(user.getRole().getRole().equals("ORG")){
            return salaRepository.findSalaBySalaId_PersonaId_IdAndSalaId_OrgId_Id(id, user.getId());
        }else{
            return salaRepository.findSalaBySalaId_PersonaId_IdAndSalaId_OrgId_Id(user.getId(), id);
        }

    }

    public List<Missatge> findAllBySala(Sala s){
        return missatgeRepository.findAllBySalaOrderByDataAsc(s);
    }


    public Missatge createMissatge(Missatge n, int receptorId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByEmail(auth.getName());

        SalaId s = new SalaId();
        if(user.getRole().getRole().equals("ORG")){
            s.setOrgId(user);
            s.setPersonaId(userRepository.findUserById(receptorId));
        }else{
            s.setOrgId(userRepository.findUserById(receptorId));
            s.setPersonaId(user);
        }

        Sala ss = salaRepository.save(new Sala(s));

        n.setSala(ss);

        n.setLlegit(false);

        n.setData(LocalDateTime.now());

        return missatgeRepository.save(n);
    }

    public List<Missatge> saveMissatges(List<Missatge> m){
        for (Missatge mi : m) {
           salaRepository.save(mi.getSala());
        }
        return missatgeRepository.saveAll(m);
    }
}
