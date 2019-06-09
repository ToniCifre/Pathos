package cv.toni.pathos.repository;


import cv.toni.pathos.model.Missatge;
import cv.toni.pathos.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("missatgeRepository")
public interface MissatgeRepository extends JpaRepository<Missatge, Long> {

    List<Missatge> findFirst5BySala_SalaId_OrgId_EmailAndLlegitFalseAndSenderOrgFalseOrderByDataDesc(String e);
    List<Missatge> findFirst5BySala_SalaId_PersonaId_EmailAndLlegitFalseAndSenderOrgTrueOrderByDataDesc(String e);



    int countAllBySala_SalaId_OrgId_EmailAndLlegitFalseAndSenderOrgFalse(String email);
    int countAllBySala_SalaId_PersonaId_EmailAndLlegitFalseAndSenderOrgTrue(String email);

    List<Missatge> findAllBySalaOrderByDataAsc(Sala s);
}