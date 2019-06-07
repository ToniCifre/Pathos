package cv.toni.pathos.repository;


import cv.toni.pathos.model.Notificacio;
import cv.toni.pathos.model.NotifyStat;
import cv.toni.pathos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("notificacioRepository")
public interface NotificacioRepository extends JpaRepository<Notificacio, Long> {

    List<Notificacio> findAllByReceptor_EmailOrderByDataDesc(String email);
    List<Notificacio> findNotificaciosByEmisor(User e);

    List<Notificacio> findAllByReceptor_EmailAndEstatOrderByDataDesc(String mail, NotifyStat stat);

    Notificacio findNotificaciosById(int id);
}