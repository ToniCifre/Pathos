package cv.toni.pathos.repository;


import cv.toni.pathos.model.Direccio;
import cv.toni.pathos.model.Notificacio;
import cv.toni.pathos.model.NotifyStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("notificacioRepository")
public interface NotificacioRepository extends JpaRepository<Notificacio, Long> {

    List<Notificacio> findNotificaciosByReceptor_EmailOrderByDataDesc(String email);

    List<Notificacio> findAllByEmisor_IdOrderByDataDesc(int id);

    List<Notificacio> findAllByReceptor_EmailAndEstatOrderByDataDesc(String mail, NotifyStat stat);

    void removeAllByDireccio(Direccio d);

    Notificacio findNotificaciosById(int id);
}