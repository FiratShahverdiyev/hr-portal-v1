package az.hrportal.hrportalapi.constant.employee;

import java.util.HashMap;

public class Kvota {
    public static final String QUOTA_1 = "20 yaşadək gənc";
    public static final String QUOTA_2 = "Yetkinlik yaşına çatmamış uşaqları tərbiyə edən tək və çoxuşaqlı valideynlər";
    public static final String QUOTA_3 = "Sağlamlıq imkanları məhdud uşaqları tərbiyə edən valideynlər";
    public static final String QUOTA_4 = "Pensiya yaşına 2 ildən az qalmış şəxslər";
    public static final String QUOTA_5 = "Əlillər və ya sağlamlıq imkanları məhdud 18 yaşınadək şəxslər";
    public static final String QUOTA_6 = "Cəzaçəkmə yerlərindən azad edilmiş vətəndaşlar";
    public static final String QUOTA_7 = "Məcburi köçkünlər";
    public static final String QUOTA_8 = "Müharibə veteranları";
    public static final String QUOTA_9 = "Şəhid ailələri";
    public static HashMap<Integer, String> quotaMap;

    private Kvota() {
        quotaMap.put(1, QUOTA_1);
        quotaMap.put(2, QUOTA_2);
        quotaMap.put(3, QUOTA_3);
        quotaMap.put(4, QUOTA_4);
        quotaMap.put(5, QUOTA_5);
        quotaMap.put(6, QUOTA_6);
        quotaMap.put(7, QUOTA_7);
        quotaMap.put(8, QUOTA_8);
        quotaMap.put(9, QUOTA_9);
    }

    public static String getKvota(Integer key) {
        if (!quotaMap.containsKey(key)) {
            throw new RuntimeException();
        }
        return quotaMap.get(key);
    }
}
