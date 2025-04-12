package org.DayPrint.core.scheduler;

import org.DayPrint.core.main;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.DayPrint.core.dayPassing;
import org.DayPrint.core.support;

@SuppressWarnings({"unused", "PointlessArithmeticExpression"})
public class checkPassingDaySchedule {

    //Time constants for easier reading
    private final static long tick = 1L;
    private final static long second = tick * 20L;
    private final static long minute = second * 60L;
    private final static long hour = minute * 60L;
    private final static long day = hour * 24L;
    private final static long dayTime = 20*minute;

    private static final long period = second * 3L;
    private static final long delay = second * 1L;

    //normal tick times
    private final static long nightStartNormal = 12542;
    private final static long nightEndNormal = 23459;

    //rain or storm tick times
    private final static long nightStartRain = 12010;
    private final static long nightEndRain = 23991;

    public static void runScheduler()
    {
        JavaPlugin instance = main.getPluginInstance();
        support.conPrint("Running Scheduler");
        World world = instance.getServer().getWorlds().getFirst();

        new BukkitRunnable() {
            long previousTime = 0;
            long currentTime = world.getTime();
            long nightStart;
            long nightEnd;

            @Override
            public void run() {
                previousTime = currentTime;
                currentTime = world.getTime();


                if(world.hasStorm()) {
                    nightStart = nightStartRain;
                    nightEnd = nightEndRain;
                }
                else {
                    nightStart = nightStartNormal;
                    nightEnd = nightEndNormal;
                }


                //current method
                if(previousTime >= nightStart+(nightEnd-nightStart)/2 && previousTime <= nightEnd)
                {
                    if(currentTime < previousTime)
                        currentTime += dayTime;
                    if(currentTime >= nightEnd) {
                        currentTime = world.getTime();
                        dayPassing.passDay();
                        previousTime = currentTime;
                    }
                }


                /*previous method
                if(previousTime >= nightEnd-period  && currentTime >= (nightEnd+period)%dayTime && currentTime <= (nightEnd+period*2L)%dayTime)
                {
                    previousTime = currentTime;
                    dayPassing.passDay();
                }
                */

            }
        }.runTaskTimer(instance, delay, period);
    }
}
