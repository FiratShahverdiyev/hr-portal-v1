package az.hrportal.hrportalapi.constant.employee;

import az.hrportal.hrportalapi.error.exception.EnumNotFoundException;

public enum EducationType {
    VISUAL(1),
    CORRESPONDENCE(2);

    private int education;

    EducationType(int education) {
        this.education = education;
    }

    public static EducationType intToEnum(int value) {
        EducationType[] values = EducationType.values();
        for (EducationType educationType : values) {
            if (educationType.education == value)
                return educationType;
        }
        throw new EnumNotFoundException(EducationType.class, value);
    }
}
