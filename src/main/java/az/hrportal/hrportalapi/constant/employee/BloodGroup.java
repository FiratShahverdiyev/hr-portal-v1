package az.hrportal.hrportalapi.constant.employee;

public enum BloodGroup {
    BIR(1),
    IKI(2),
    UC(3),
    DORT(4);

    private int bloodGroup;

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
