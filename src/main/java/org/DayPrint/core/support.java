package org.DayPrint.core;

import org.bukkit.entity.Player;

public class support {

    public static void conPrint(String print) {

        System.out.println("[CONSOLE]: " + print);
    }


    public static void worldPrint(String label, String print) {
        main.getPluginInstance().getServer().broadcastMessage("[" + label + "]: " + print);
    }

    public static void playerPrint(String label, String print, Player player)
    {
        player.sendRawMessage("[" + label + "]: " + print);
    }

}
