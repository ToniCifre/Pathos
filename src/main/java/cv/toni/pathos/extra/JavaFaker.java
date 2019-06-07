package cv.toni.pathos.extra;

import com.github.javafaker.Faker;
import cv.toni.pathos.model.*;
import cv.toni.pathos.service.DireccioService;
import cv.toni.pathos.service.MissatgeService;
import cv.toni.pathos.service.NotificacioService;
import cv.toni.pathos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class JavaFaker {


    @Autowired
    private DireccioService direccioService;
    @Autowired
    private UserService userService;
    @Autowired
    private NotificacioService notificacioService;
    @Autowired
    private MissatgeService missatgeService;

    private Faker faker;
    private Role org, col, per;

    private List<User> userList;
    private List<Direccio> directionList;
    private List<Notificacio> notifyList;
    private List<Missatge> missatgeList;

    @RequestMapping(value = {"/faker"}, method = RequestMethod.GET)
    public ModelAndView JavaFaker() {
        faker = new Faker();
        org = userService.getRole("ORG");
        col = userService.getRole("COL");
        per = userService.getRole("PER");

        generateUsers();
        generateDirections();
        generateNotifications();
        generateMissatge();

        return new ModelAndView("redirect:/");
    }

    void generateUsers() {
        userList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            User u = new User();
            u.setActive(1);
            u.setEmail(faker.bothify("org###@gmail.com"));
            u.setPassword("tonicifre");
            u.setName(faker.name().firstName());
            u.setRole(org);
            u.setPhoto("user/userDefault.jpg");
            userList.add(u);
        }
        for (int i = 0; i < 30; i++) {
            User u = new User();
            u.setActive(1);
            u.setEmail(faker.bothify("col???###@gmail.com"));
            u.setPassword("tonicifre");
            u.setName(faker.bothify(faker.name().firstName() + "####"));
            u.setRole(col);
            u.setOrgId(userList.get(i % 3));
            u.setPhoto("user/userDefault.jpg");
            userList.add(u);
        }
        for (int i = 0; i < 500; i++) {
            User u = new User();
            u.setActive(1);
            u.setEmail(faker.bothify("per???#####@gmail.com"));
            u.setPassword("tonicifre");
            u.setName(faker.bothify(faker.name().firstName() + "#####?"));
            u.setRole(per);
            u.setPhoto("user/userDefault.jpg");
            userList.add(u);
        }

        userList = userService.saveUsers(userList);
    }

    void generateDirections() {
        directionList = new ArrayList<>();

        for (User u : userList) {
            Direccio d = new Direccio();
            d.setDireccio(faker.address().streetAddress());
            d.setCp(8001);
            d.setUser(u);
            directionList.add(d);
        }

        directionList = direccioService.savedirections(directionList);
    }

    void generateNotifications() {
        notifyList = new ArrayList<>();

        Date d1 = new GregorianCalendar(2018, Calendar.AUGUST, 15).getTime();
        Date d2 = new GregorianCalendar().getTime();

        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant;

        for (int x = 0; x < 2; x++) {
            for (int i = 33; i < userList.size(); i++) {
                Notificacio n = new Notificacio();
                n.setTitol(faker.hacker().adjective());
                n.setDescripcio(faker.bothify("?????? ???????????? ??????????? ??????? ?????????? ????????????" +
                        "???? ??????????????????? ?????????? ?????? ??? ???????? ???????? ???????? ?????? ????? ???? " +
                        "????????? ??????????????? ?????? ?????????????? ???????? ???? ?????? ????? ??????? ?????????" +
                        "?? ?????? ?????? ?????? ????? ??????? ??????????? ??????? ??????? ??????? ????? ?? ????????"));

                n.setProduct(System.currentTimeMillis() % 2 == 0);

                Date d = faker.date().between(d1, d2);
                instant = d.toInstant();
                n.setData(instant.atZone(defaultZoneId).toLocalDateTime());


                if (n.isProduct()) {
                    SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyyy");
                    n.setCaducitat(dt.format(d));
                }

                n.setEstat(NotifyStat.getRandomEstat());
                n.setEmisor(userList.get(i));
                n.setReceptor(userList.get(i % 3));
                n.setDireccio(directionList.get(i));

                notifyList.add(n);
            }
        }
        notifyList = notifyList.stream().sorted(Comparator.comparing(Notificacio::getData)).collect(Collectors.toList());

        notifyList = notificacioService.saveNotifications(notifyList);
    }

    void generateMissatge() {
        missatgeList = new ArrayList<>();

        Date d1 = new GregorianCalendar(2018, Calendar.AUGUST, 15).getTime();
        Date d2 = new GregorianCalendar().getTime();

        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant;

        for (int i = 33; i < userList.size(); i++) {
            Missatge m = new Missatge();

            m.setMsg(faker.bothify("?????? ???????????? ??????????? ??????? ?????????? ????????????" +
                    "???? ???????????????????"));

            instant = faker.date().between(d1, d2).toInstant();
            m.setData(instant.atZone(defaultZoneId).toLocalDateTime());

            m.setSala(new Sala(new SalaId(userList.get(i) ,userList.get(i%3))));

            m.setLlegit(false);

            missatgeList.add(m);
        }

        missatgeList = missatgeList.stream().sorted(Comparator.comparing(Missatge::getData)).collect(Collectors.toList());


        missatgeService.saveMissatges(missatgeList);
    }
}