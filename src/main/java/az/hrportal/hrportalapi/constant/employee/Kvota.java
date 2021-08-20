package az.hrportal.hrportalapi.constant.employee;

import java.util.HashMap;

public class Kvota {
    public static final String KVOTA_1 = "20 yaşadək gənc";
    public static final String KVOTA_2 = "Yetkinlik yaşına çatmamış uşaqları tərbiyə edən tək və çoxuşaqlı valideynlər";
    public static final String KVOTA_3 = "Sağlamlıq imkanları məhdud uşaqları tərbiyə edən valideynlər";
    public static final String KVOTA_4 = "Pensiya yaşına 2 ildən az qalmış şəxslər";
    public static final String KVOTA_5 = "Əlillər və ya sağlamlıq imkanları məhdud 18 yaşınadək şəxslər";
    public static final String KVOTA_6 = "Cəzaçəkmə yerlərindən azad edilmiş vətəndaşlar";
    public static final String KVOTA_7 = "Məcburi köçkünlər";
    public static final String KVOTA_8 = "Müharibə veteranları";
    public static final String KVOTA_9 = "Şəhid ailələri";
    public static HashMap<Integer, String> kvotaMap;

    private Kvota() {
        kvotaMap.put(1, KVOTA_1);
        kvotaMap.put(2, KVOTA_2);
        kvotaMap.put(3, KVOTA_3);
        kvotaMap.put(4, KVOTA_4);
        kvotaMap.put(5, KVOTA_5);
        kvotaMap.put(6, KVOTA_6);
        kvotaMap.put(7, KVOTA_7);
        kvotaMap.put(8, KVOTA_8);
        kvotaMap.put(9, KVOTA_9);
    }

    public static String getKvota(Integer key) {
        if (!kvotaMap.containsKey(key)) {
            throw new RuntimeException();
        }
        return kvotaMap.get(key);
    }
}
