package az.hrportal.hrportalapi.constant.employee;

public enum EducationType {
    VISUAL(1),
    CORRESPONDENCE(2);

    private int education;

    EducationType(int education) {
        this.education = education;
    }

    public EducationType intToEnum(int value) {
        EducationType[] values = EducationType.values();
        for (EducationType educationType : values) {
            if (educationType.education == value)
                return educationType;
        }
        throw new RuntimeException();
    }
}
