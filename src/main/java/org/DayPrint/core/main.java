package org.DayPrint.core;

import org.DayPrint.core.scheduler.checkPassingDaySchedule;
import org.DayPrint.core.Commands.CommandSetDay;
import org.DayPrint.core.ConfigHandling.ConfigHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public final class main extends JavaPlugin{

    private static JavaPlugin pluginInstance;
    private static ConfigHandler ch;
    private static HashMap<String,BukkitTask> tasks;

    @Override
    public void onEnable() {
        String CONFIG_NAME = "DayPrint_config.yml";

        // Plugin startup logic
        pluginInstance = this;
        ch = new ConfigHandler(CONFIG_NAME);

        try{
            ch.loadGenerateConfig();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        // add Listeners
        getServer().getPluginManager().registerEvents(new dayPassing(),this);

        //add commands
        Objects.requireNonNull(getCommand("setDay")).setExecutor(new CommandSetDay());

        //start scheduler
        tasks = new HashMap<>();
        tasks.put("dayCycle",checkPassingDaySchedule.runScheduler().runTaskTimer(this, constants.delay, constants.period));

        System.out.println("INSTALLED, WORKING CORRECTLY: " + getClass().getName().split("\\.")[1]);

    }

    public static JavaPlugin getPluginInstance()
    {   return pluginInstance; }

    public static ConfigHandler getConfigHandler()
    {   return ch;  }

    public static BukkitTask getTask(String key)
    {
        return tasks.get(key);
    }


}
