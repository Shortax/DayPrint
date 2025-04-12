package org.DayPrint.core.scheduler;

import org.DayPrint.core.constants;
import org.DayPrint.core.main;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.DayPrint.core.dayPassing;
import org.DayPrint.core.support;

public class checkPassingDaySchedule {

    private static long pause = 0;

    public static BukkitRunnable runScheduler()
    {
        JavaPlugin instance = main.getPluginInstance();
        support.conPrint("Running Scheduler");
        World world = instance.getServer().getWorlds().getFirst();


        return new BukkitRunnable() {
            long previousTime = 0;
            long currentTime = world.getTime();
            long nightStart;
            long nightEnd;

            final long nightStartRain = constants.nightStartRain;
            final long nightEndRain = constants.nightEndRain;
            final long nightStartNormal = constants.nightStartNormal;
            final long nightEndNormal = constants.nightEndNormal;

            final long dayTime = constants.dayTime;

            @Override
            public void run() {
                previousTime = currentTime;
                currentTime = world.getTime();

                if(pause > 0)
                {
                    pause-=1;
                }
                else{
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
                }
            }
        };

    }

    public static void pauseSchedule(long cycles)
    {
        pause = cycles;
    }
}
