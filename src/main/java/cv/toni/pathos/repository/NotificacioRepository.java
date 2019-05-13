package cv.toni.pathos.repository;


import cv.toni.pathos.model.Notificacio;
import cv.toni.pathos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("notificacioRepository")
public interface NotificacioRepository extends JpaRepository<Notificacio, Long> {

    List<Notificacio> findNotificaciosByReceptorId(int r);
    List<Notificacio> findNotificaciosByEmisorId(int e);

    List<Notificacio> findNotificaciosByReceptor_Email(String email);
    List<Notificacio> findNotificaciosByEmisor(User e);
}