package az.hrportal.hrportalapi.constant.employee;

public enum MilitaryAchievement {
    MILITARY_SUCCESSFULLY(1),
    UNFIT(2),
    LIMITED_USEFUL(3),
    MILITARY_DATE(4);

    private int value;

    MilitaryAchievement(int value) {
        this.value = value;
    }

    public MilitaryAchievement intToEnum(int value) {
        MilitaryAchievement[] values = MilitaryAchievement.values();
        for (MilitaryAchievement achievement : values) {
            if (achievement.value == value)
                return achievement;
        }
        throw new RuntimeException();
    }
}
