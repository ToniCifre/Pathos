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
import java.util.ArrayList;
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
        try {
            if(userRepository.findUserByEmail(auth.getName()).getRole().getRole().equals("ORG")){
                return missatgeRepository.countAllBySala_SalaId_OrgId_EmailAndLlegitFalseAndSenderOrgFalse(auth.getName());
            }else{
                return missatgeRepository.countAllBySala_SalaId_PersonaId_EmailAndLlegitFalseAndSenderOrgTrue(auth.getName());
            }
        }catch (Exception e){return 0;}

    }
    public List<Missatge> find5Missatger(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            if(userRepository.findUserByEmail(auth.getName()).getRole().getRole().equals("ORG")){
                return missatgeRepository.findFirst5BySala_SalaId_OrgId_EmailAndLlegitFalseAndSenderOrgFalseOrderByDataDesc(auth.getName());
            }else{
                return missatgeRepository.findFirst5BySala_SalaId_PersonaId_EmailAndLlegitFalseAndSenderOrgTrueOrderByDataDesc(auth.getName());
            }
        }catch (Exception e){return new ArrayList<>();}
    }
    public List<Sala> findAllSalas(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            if (userRepository.findUserByEmail(auth.getName()).getRole().getRole().equals("ORG")) {
                return salaRepository.findSalasBySalaId_OrgId_Email(auth.getName());
            } else {
                return salaRepository.findSalasBySalaId_PersonaId_Email(auth.getName());
            }
        }catch (Exception e){return new ArrayList<>();}
    }

    public Sala findSala(int id, User user){
        try {
            if(user.getRole().getRole().equals("ORG")){
                return salaRepository.findSalaBySalaId_PersonaId_IdAndSalaId_OrgId_Id(id, user.getId());
            }else{
                return salaRepository.findSalaBySalaId_PersonaId_IdAndSalaId_OrgId_Id(user.getId(), id);
            }
        }catch (Exception e){
            SalaId s = new SalaId();
            if(user.getRole().getRole().equals("ORG")){
                s.setOrgId(user);
                s.setPersonaId(userRepository.findUserById(id));
            }else{
                s.setOrgId(userRepository.findUserById(id));
                s.setPersonaId(user);
            }

            return salaRepository.save(new Sala(s));
        }
    }

    public List<Missatge> findAllBySala(Sala s){
        try {
            return missatgeRepository.findAllBySalaOrderByDataAsc(s);
        }catch (Exception e){return new ArrayList<>();}
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

        if(user.getRole().getRole().equals("ORG")){
            n.setOrg(true);
        }else{
            n.setOrg(false);
        }
        n.setLlegit(false);

        n.setData(LocalDateTime.now());

        return missatgeRepository.save(n);
    }


    public void llegirMissatge(List<Missatge> missatges, User user){
        if(user.getRole().getRole().equals("ORG")){
            for (Missatge m: missatges) {
                if(!m.idSenderOrg()){
                    m.setLlegit(true);
                }
            }
        }else{
            for (Missatge m: missatges) {
                if(m.idSenderOrg()){
                    m.setLlegit(true);
                }
            }
        }

        missatgeRepository.saveAll(missatges);
    }

    public List<Missatge> saveMissatges(List<Missatge> m){
        for (Missatge mi : m) {
           mi.setSala(salaRepository.save(mi.getSala()));
        }
        return missatgeRepository.saveAll(m);
    }
}
