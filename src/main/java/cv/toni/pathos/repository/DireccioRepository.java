package cv.toni.pathos.repository;

import cv.toni.pathos.model.Direccio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("direccioRepository")
public interface DireccioRepository extends JpaRepository<Direccio, Long> {

    Direccio findByCp(int cp);
    List<Direccio> findAllByCarrerAndNum(String carrer, int num);
}