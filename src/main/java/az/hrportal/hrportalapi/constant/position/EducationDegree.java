package az.hrportal.hrportalapi.constant.position;

import az.hrportal.hrportalapi.error.exception.EnumNotFoundException;

public enum EducationDegree {
    MIDDLE(1),
    MIDDLE_SPECIAL(2),
    PROFESSION(3),
    BACHELOR(4),
    MASTER(5),
    DOCTORAL(6);

    private int degree;

    EducationDegree(int degree) {
        this.degree = degree;
    }

    public static EducationDegree intToEnum(int value) {
        EducationDegree[] values = EducationDegree.values();
        for (EducationDegree educationDegree : values) {
            if (educationDegree.degree == value)
                return educationDegree;
        }
        throw new EnumNotFoundException(EducationDegree.class, value);
    }
}
