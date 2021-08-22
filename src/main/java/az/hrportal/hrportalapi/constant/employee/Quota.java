package az.hrportal.hrportalapi.constant.employee;

import az.hrportal.hrportalapi.error.exception.EnumNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Set;

@Component
public class Quota {
    private static final String QUOTA_1 = "20 yaşadək gənc";
    private static final String QUOTA_2 = "Yetkinlik yaşına çatmamış uşaqları tərbiyə edən tək və " +
            "çoxuşaqlı valideynlər";
    private static final String QUOTA_3 = "Sağlamlıq imkanları məhdud uşaqları tərbiyə edən valideynlər";
    private static final String QUOTA_4 = "Pensiya yaşına 2 ildən az qalmış şəxslər";
    private static final String QUOTA_5 = "Əlillər və ya sağlamlıq imkanları məhdud 18 yaşınadək şəxslər";
    private static final String QUOTA_6 = "Cəzaçəkmə yerlərindən azad edilmiş vətəndaşlar";
    private static final String QUOTA_7 = "Məcburi köçkünlər";
    private static final String QUOTA_8 = "Müharibə veteranları";
    private static final String QUOTA_9 = "Şəhid ailələri";
    private static HashMap<Integer, String> quotaMap = new HashMap<>();

    @PostConstruct
    private void initQuotaMap() {
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

    public static String getQuota(Integer key) {
        if (!quotaMap.containsKey(key)) {
            throw new EnumNotFoundException(Quota.class, key);
        }
        return quotaMap.get(key);
    }

    public static Set<Integer> getQuotaMapKeySet() {
        return quotaMap.keySet();
    }
}
