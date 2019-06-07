package cv.toni.pathos.repository;


import cv.toni.pathos.model.Missatge;
import cv.toni.pathos.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("missatgeRepository")
public interface MissatgeRepository extends JpaRepository<Missatge, Long> {

    List<Missatge> findAllBySala_SalaId_OrgId_EmailOrderByDataDesc(String r);
    List<Missatge> findAllBySala_SalaId_PersonaId_EmailOrderByDataDesc(String e);

    int countAllBySala_SalaId_OrgId_Email(String email);

    List<Missatge> findAllBySala(Sala s);
}