package cv.toni.pathos.repository;

import cv.toni.pathos.model.Direccio;
import cv.toni.pathos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("direccioRepository")
public interface DireccioRepository extends JpaRepository<Direccio, Long> {

    Direccio findById(int id);
    List<Direccio> findDirecciosByUser(User u);
}