package az.hrportal.hrportalapi.helper;

import az.hrportal.hrportalapi.constant.Status;
import az.hrportal.hrportalapi.domain.position.Position;

public class CommonHelper {
    public static boolean checkStatus(Object object) {
        if (object instanceof Position) {
            if (((Position) object).getStatus().equals(Status.APPROVED))
                return true;
        }
        return false;
    }
}
