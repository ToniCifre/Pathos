package cv.toni.pathos.repository;


import cv.toni.pathos.model.Missatge;
import cv.toni.pathos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("missatgeRepository")
public interface MissatgeRepository extends JpaRepository<Missatge, Long> {

    List<Missatge> findMissatgesByReceptorId(int id);
    List<Missatge> findMissatgesByEmisorId(int id);

    List<Missatge> findMissatgesByReceptor(User r);
    List<Missatge> findMissatgesByEmisor(User e);
}