package az.hrportal.hrportalapi.constant.employee;

import az.hrportal.hrportalapi.error.exception.EnumNotFoundException;

public enum Series {
    AZE(1);

    private int series;

    public int getSeries() {
        return series;
    }

    Series(int series) {
        this.series = series;
    }

    public static Series intToEnum(int value) {
        Series[] values = Series.values();
        for (Series series : values) {
            if (series.series == value)
                return series;
        }
        throw new EnumNotFoundException(Series.class, value);
    }
}
