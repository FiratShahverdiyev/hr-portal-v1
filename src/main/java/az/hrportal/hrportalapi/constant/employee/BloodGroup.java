package az.hrportal.hrportalapi.constant.employee;

import lombok.Getter;

@Getter
public enum BloodGroup {

    ONE_PLUS("(I)+"),
    TWO_PLUS("(II)+"),
    THREE_PLUS("(III)+"),
    FOUR_PLUS("(IV)+"),
    ONE_MINUS("(I)-"),
    TWO_MINUS("(II)-"),
    THREE_MINUS("(III)-"),
    FOUR_MINUS("(IV)-");

    private String value;

    BloodGroup(String value) {
        this.value = value;
    }

}
