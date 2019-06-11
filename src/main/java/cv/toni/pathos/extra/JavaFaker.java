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

    Date d1;
    Date d2;

    @RequestMapping(value = {"/faker"}, method = RequestMethod.GET)
    public ModelAndView JavaFaker() {
        faker = new Faker();
        org = userService.getRole("ORG");
        col = userService.getRole("COL");
        per = userService.getRole("PER");

        d1 = new GregorianCalendar(2018, Calendar.AUGUST, 15).getTime();
        d2 = new GregorianCalendar().getTime();


        generateUsers();
        generateDirections();
        generateNotifications();
        generateMissatge();

        return new ModelAndView("redirect:/");
    }

    void generateUsers() {
        userList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            User u = new User();
            u.setActive(1);
            u.setEmail(faker.bothify("org###@gmail.com"));
            u.setPassword("tonicifre");
            u.setName(faker.name().firstName());
            u.setRole(org);
            u.setPhoto("user/userDefault.jpg");
            u.setColaboradors(new ArrayList<>());
            for (int x = 0; x < 10; x++) {
                User c = new User();
                c.setActive(1);
                c.setEmail(faker.bothify("col???###@gmail.com"));
                c.setPassword("tonicifre");
                c.setName(faker.bothify(faker.name().firstName() + "####"));
                c.setRole(col);
                c.setPhoto("user/userDefault.jpg");
                c.setColaboradors(new ArrayList<>());
                c.setOrg(u);
                u.addColaborador(c);
            }
            userList.add(u);
        }

        for (int i = 0; i < 995; i++) {
            User u = new User();
            u.setActive(1);
            u.setEmail(faker.bothify("per???#####@gmail.com"));
            u.setPassword("tonicifre");
            u.setName(faker.bothify(faker.name().firstName() + "#####?"));
            u.setRole(per);
            u.setPhoto("user/userDefault.jpg");
            u.setColaboradors(new ArrayList<>());
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
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant;
        notifyList = new ArrayList<>();

        for (Direccio direccio : directionList) {
            for (int i = 0; i < 5; i++) {
                Notificacio n = new Notificacio();
                n.setTitol(faker.hacker().adjective());
                n.setDescripcio(faker.bothify("?????? ???????????? ??????????? ??????? ?????????? ????????????" +
                        "???? ??????????????????? ?????????? ?????? ??? ???????? ???????? ???????? ?????? ????? ???? " +
                        "????????? ??????????????? ?????? ?????????????? ???????? ???? ?????? ????? ??????? ?????????" +
                        "?? ?????? ?????? ?????? ????? ??????? ??????????? ??????? ??????? ??????? ????? ?? ????????"));

                Date d = faker.date().between(d1, d2);
                instant = d.toInstant();
                n.setData(instant.atZone(defaultZoneId).toLocalDateTime());

                n.setProduct(faker.bool().bool());
                if (n.isProduct()) {
                    SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyyy");
                    n.setCaducitat(dt.format(d));
                }

                n.setEstat(NotifyStat.getRandomEstat());
                n.setReceptor(userList.get(i % 3));
                n.setEmisor(userList.get(faker.number().numberBetween(35,500)));
                n.setDireccio(direccio);
                notifyList.add(n);
            }
        }
        notifyList = notifyList.stream().sorted(Comparator.comparing(Notificacio::getData)).collect(Collectors.toList());
        notifyList = notificacioService.saveNotifications(notifyList);
    }


    void generateMissatge() {
        missatgeList = new ArrayList<>();

        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant;

        for (int i = 0; i < 1000; i++) {
            Missatge m = new Missatge();

            m.setMsg(faker.bothify("?????? ???????????? ??????????? ??????? ?????????? ????????????" +
                    "???? ???????????????????"));

            instant = faker.date().between(d1, d2).toInstant();
            m.setData(instant.atZone(defaultZoneId).toLocalDateTime());

            int x = faker.number().numberBetween(100,userList.size()-1);
            m.setSala(new Sala(new SalaId(userList.get(x) ,userList.get(i%3)),userList.get(i%3),userList.get(x) ));

            m.setLlegit(false);
            m.setOrg(faker.bool().bool());

            missatgeList.add(m);
        }

        missatgeList = missatgeList.stream().sorted(Comparator.comparing(Missatge::getData)).collect(Collectors.toList());


        missatgeService.saveMissatges(missatgeList);
    }
}