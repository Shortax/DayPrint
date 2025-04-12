package org.DayPrint.core.ConfigHandling;

import org.DayPrint.core.main;
import org.DayPrint.core.support;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;


@SuppressWarnings("FieldMayBeFinal")
public class ConfigHandler {

    private final String CONFIG_NAME;
    private File configFileLocation;
    private FileConfiguration ymlLoader;
    private final HashMap<String,Object> defaultValues;
    private HashMap<String, ConfigEntry> conEntries;

    public ConfigHandler(String confName )
    {
        CONFIG_NAME = confName;

        // SET DEFAULT VALUES TO CONFIG FILE
        defaultValues = new HashMap<>();
        defaultValues.put("days.passed",0);

        conEntries = new HashMap<>();
    }

    public void loadGenerateConfig() throws IOException
    {
        File plugFolder = main.getPluginInstance().getDataFolder();

        configFileLocation = new File(plugFolder,CONFIG_NAME);

        String print = "";

        boolean generated = false;

        if(!configFileLocation.exists()) {
            generated = true;
            boolean result1 = true;
            boolean result2 = true;

            if(!plugFolder.exists())
                result1 = plugFolder.mkdirs();

            if(result1){
                result2 = configFileLocation.createNewFile();
                print = "Config File Generated";
            }

            if(!result1 || !result2) {
                support.conPrint("Failed to generate");
                throw new IOException();
            }

        }
        else
            print = "Config File Loaded";

        support.conPrint(print);

        ymlLoader = YamlConfiguration.loadConfiguration(configFileLocation);
        for(String key : defaultValues.keySet()) {
            conEntries.put(key.split("\\.")[0],new ConfigEntry(ymlLoader,key));
            if(generated || ymlLoader.get(key) == null)
                ymlLoader.set(key, defaultValues.get(key));
        }
        saveConf();
    }

    public void setConfigEntry(String entry, Object newValue) {
        ConfigEntry ce = conEntries.get(entry);
        ce.setValue(newValue);
        saveConf();
    }

    public Object getEntryFromConfig(String entry)
    {
        return conEntries.get(entry).getValue();
    }

    private void saveConf() {
        try {
            ymlLoader.save(configFileLocation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


@SuppressWarnings("FieldMayBeFinal")
class ConfigEntry
{
    private String path;
    private FileConfiguration parent;

    ConfigEntry(FileConfiguration parentConf,String p){
        parent = parentConf;
        path = p;
    }

    Object getValue(){
        return parent.get(path);
    }

    void setValue(Object nValue){
        parent.set(path,nValue);
    }
}
