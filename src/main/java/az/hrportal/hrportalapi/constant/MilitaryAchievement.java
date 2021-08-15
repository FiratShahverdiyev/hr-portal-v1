package az.hrportal.hrportalapi.constant;

public enum MilitaryAchievement {
    Hərbi_mükəlləfiyyətli(1),
    Yararsız(2),
    Məhdud_yararlı(3),
    Hərbidə_olma_tarixi(4);

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
        return Məhdud_yararlı;
    }
}
