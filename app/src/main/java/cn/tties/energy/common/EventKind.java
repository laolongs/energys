package cn.tties.energy.common;

/**
 * eventbus 事件类
 */
public class EventKind {
    //登录服务
    public static final String EVENT_LOGIN = "EVENT_LOGIN";
    //测量点数据同步
    public static final String EVENT_METER_SYCN = "EVENT_METER_SYCN";
    //电费数据同步
    public static final String EVENT_HOME_SYCN = "EVENT_HOME_SYCN";
    //电费数据同步
    public static final String EVENT_STATMENT_ENERGY_SYCN = "EVENT_STATMENT_ENERGY_SYCN";
    //电量详情数据同步
    public static final String EVENT_HOME_HISTORY_ENERGY_SYCN = "EVENT_HOME_HISTORY_ENERGY_SYCN";
    //电量详情数据同步
    public static final String EVENT_MONITOR_HISTORY_ENERGY_SYCN = "EVENT_MONITOR_HISTORY_ENERGY_SYCN";
    //数据统计同步
    public static final String EVENT_ANAYSIS_ENERGY_SYCN = "EVENT_ANAYSIS_ENERGY_SYCN";
    //吞量分析同步
    public static final String EVENT_THROUGHPUT_ANALYSIS_SYCN = "EVENT_THROUGHPUT_ANALYSIS_SYCN";
    //检查版本
    public static final String EVENT_VERSION_SYCN = "EVENT_VERSION_SYCN";

}