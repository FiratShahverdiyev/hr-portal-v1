package az.hrportal.hrportalapi.constant;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Constant {
    public static final String dateFormat = "MM-dd-yyyy";
    public static final Float DSMF = 0.5f;
    public static final Float INCOME_TAX = 14f;
    public static final Float ITS = 2f;
    public static final Float TRADE_UNION = 2f;
    public static final Float UNEMPLOYMENT_INSURANCE = 0.5f;
    public static final Set<DocumentType> passiveDayDocuments = new HashSet<>(Arrays
            .asList());
}
