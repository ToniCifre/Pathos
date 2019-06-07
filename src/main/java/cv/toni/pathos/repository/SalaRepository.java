package cv.toni.pathos.repository;


import cv.toni.pathos.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("salaRepository")
public interface SalaRepository extends JpaRepository<Sala, Long> {
    List<Sala> findSalasBySalaId_OrgId_Email(String email);
    List<Sala> findSalasBySalaId_PersonaId_Email(String email);
}