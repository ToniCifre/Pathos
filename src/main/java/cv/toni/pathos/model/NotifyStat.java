package cv.toni.pathos.model;

import java.util.Random;

public enum NotifyStat {PENDENT, ACCEPTAT, RECOLLIT, CANCELAT, DENEGAT;


    public static NotifyStat getRandomEstat() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}