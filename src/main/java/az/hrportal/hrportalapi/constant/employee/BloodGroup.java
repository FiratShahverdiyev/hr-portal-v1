package az.hrportal.hrportalapi.constant.employee;

public enum BloodGroup {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4);

    private int bloodGroup;

    public int getBloodGroup() {
        return bloodGroup;
    }

    BloodGroup(int bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public static BloodGroup intToEnum(int value) {
        BloodGroup[] values = BloodGroup.values();
        for (BloodGroup bloodGroup : values) {
            if (bloodGroup.bloodGroup == value)
                return bloodGroup;
        }
        throw new RuntimeException();
    }
}
