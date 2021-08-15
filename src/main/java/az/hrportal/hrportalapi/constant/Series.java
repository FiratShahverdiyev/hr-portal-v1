package az.hrportal.hrportalapi.constant;

public enum Series {
    AZE(1);

    private int series;

    Series(int series) {
        this.series = series;
    }

    private Series intToEnum(int value) {
        Series[] values = Series.values();
        for (Series series : values) {
            if (series.series == value)
                return series;
        }
        return AZE;
    }
}
