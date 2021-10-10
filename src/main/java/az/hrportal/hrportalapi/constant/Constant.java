package az.hrportal.hrportalapi.constant;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Constant {
    public static final String dateFormat = "MM-dd-yyyy";
    public static final float DSMF = 3F;
    public static final float LESS_THAN_2500_INCOME_TAX = 14f;
    public static final float MORE_THAN_2500_INCOME_TAX = 25f;
    public static final float ITS = 2f;
    public static final float TRADE_UNION = 2f;
    public static final float UNEMPLOYMENT_INSURANCE = 0.5f;
    public static final Set<DocumentType> passiveDayDocuments = new HashSet<>(Arrays
            .asList());
    public static final String timeZone = "Asia/Baku";
}
