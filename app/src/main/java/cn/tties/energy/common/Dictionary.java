package cn.tties.energy.common;

/**
 * author chensi
 */
public class Dictionary {
    public class DateType {
        public static final int DAY = 1;
        public static final int WEEK = 2;
        public static final int MONTH = 3;
        public static final int YEAR = 4;
    }

    public class ObjType {
        public static final int LEDGER = 1;
        public static final int METER = 2;
    }

    public class ElectricType {
        public static final int SINGLE = 0;
        public static final int MULTI = 1;
    }

    public class AnalysisDateType {
        public static final int DAY = 1;
        public static final int WEEK = 4;
        public static final int MONTH = 2;
        public static final int YEAR = 3;
    }

    public class EngryType {
        public static final int ENGRY = 1;
        public static final int POWER = 2;
        public static final int ACTIVE_POWER = 3;
        public static final int REACTIVE_POWER = 4;
        public static final int VOLTAGE = 5;
        public static final int CURRENT = 6;
    }
}
