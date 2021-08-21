package az.hrportal.hrportalapi.constant.position;

import az.hrportal.hrportalapi.error.exception.EnumNotFoundException;

public enum SubWorkCalculateDegree {
    A(1),
    B(2);

    private int value;

    SubWorkCalculateDegree(int value) {
        this.value = value;
    }

    private SubWorkCalculateDegree intToEnum(int value) {
        SubWorkCalculateDegree[] values = SubWorkCalculateDegree.values();
        for (SubWorkCalculateDegree subWorkCalculateDegree : values) {
            if (subWorkCalculateDegree.value == value)
                return subWorkCalculateDegree;
        }
        throw new EnumNotFoundException(SubWorkCalculateDegree.class, value);
    }
}
