package az.hrportal.hrportalapi.constant.position;

import az.hrportal.hrportalapi.error.exception.EnumNotFoundException;

public enum VacancyCategory {
    LEADER(1),
    ENGINEER(2),
    TECHNICAL_EXECUTOR(3),
    WORKER(4);

    private int value;

    VacancyCategory(int value) {
        this.value = value;
    }

    private VacancyCategory intToEnum(int value) {
        VacancyCategory[] values = VacancyCategory.values();
        for (VacancyCategory vacancyCategory : values) {
            if (vacancyCategory.value == value)
                return vacancyCategory;
        }
        throw new EnumNotFoundException(VacancyCategory.class, value);
    }
}
