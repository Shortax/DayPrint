package org.DayPrint.core;

@SuppressWarnings({"PointlessArithmeticExpression", "unused"})
public class constants {
    //Time constants for easier reading
    public final static long tick = 1L;
    public final static long second = tick * 20L;
    public final static long minute = second * 60L;
    public final static long dayTime = 20*minute;

    public static final long period = second * 3L;
    public static final long delay = second * 1L;

    //normal tick times
    public final static long nightStartNormal = 12542;
    public final static long nightEndNormal = 23459;

    //rain or storm tick times
    public final static long nightStartRain = 12010;
    public final static long nightEndRain = 23991;
}
