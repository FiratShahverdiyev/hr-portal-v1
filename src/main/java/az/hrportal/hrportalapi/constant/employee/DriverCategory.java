package az.hrportal.hrportalapi.constant.employee;

public enum DriverCategory {
    A(1),
    B(2),
    C(3),
    D(4);
    private int category;

    DriverCategory(int category) {
        this.category = category;
    }

    public DriverCategory intToEnum(int value) {
        DriverCategory[] values = DriverCategory.values();
        for (DriverCategory driverCategory : values) {
            if (driverCategory.category == value)
                return driverCategory;
        }
        throw new RuntimeException();
    }
}
