package cv.toni.pathos.repository;


import cv.toni.pathos.model.Missatge;
import cv.toni.pathos.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("missatgeRepository")
public interface MissatgeRepository extends JpaRepository<Missatge, Long> {

    List<Missatge> findFirst5BySala_SalaId_OrgId_EmailAndSenderOrgTrueOrderByDataDesc(String e);
    List<Missatge> findFirst5BySala_SalaId_PersonaId_EmailAndSenderOrgTrueOrderByDataDesc(String e);



    int countAllBySala_SalaId_OrgId_Email(String email);

    List<Missatge> findAllBySalaOrderByDataAsc(Sala s);
}