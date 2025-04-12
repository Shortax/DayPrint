package org.DayPrint.core;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.TimeSkipEvent;

public class dayPassing implements Listener {

    @EventHandler
    public void onDayPass(TimeSkipEvent event) {

        support.conPrint( ("TIME SKIPPED | REASON: " + event.getSkipReason()) );

        if(event.getSkipReason().equals(TimeSkipEvent.SkipReason.NIGHT_SKIP)){
            passDay();
        }
    }

    public static void passDay()
    {
        String entry = "days";

        int amountDays = (int) main.getConfigHandler().getEntryFromConfig(entry) + 1;

        support.worldPrint("#","Day: " + amountDays);

        main.getConfigHandler().setConfigEntry(entry,amountDays);
    }
}
