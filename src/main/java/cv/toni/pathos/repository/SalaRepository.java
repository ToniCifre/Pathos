package cv.toni.pathos.repository;


import cv.toni.pathos.model.Sala;
import cv.toni.pathos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("salaRepository")
public interface SalaRepository extends JpaRepository<Sala, User> {
    List<Sala> findSalasBySalaId_OrgId_Email(String email);
    List<Sala> findSalasBySalaId_PersonaId_Email(String email);


    Sala findSalaBySalaId_PersonaId_IdAndSalaId_OrgId_Id(int id, int id2);
}