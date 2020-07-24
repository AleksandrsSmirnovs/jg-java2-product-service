package productService.util;

import java.math.BigDecimal;

public class Utils {

    public static boolean bigDecimalEquals(BigDecimal a, BigDecimal b) {
        return a == null && b == null || a != null && b != null && a.compareTo(b) == 0;
    }
}
