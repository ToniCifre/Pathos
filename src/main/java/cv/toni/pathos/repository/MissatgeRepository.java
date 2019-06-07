package cv.toni.pathos.repository;


import cv.toni.pathos.model.Missatge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("missatgeRepository")
public interface MissatgeRepository extends JpaRepository<Missatge, Long> {

    List<Missatge> findAllByReceptor_EmailOrderByDataDesc(String r);
    List<Missatge> findAllByEmisor_EmailOrderByDataDesc(String e);

    int countReceptor_EmailAndLlegitIsFalse(String email);
}