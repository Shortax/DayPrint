package org.DayPrint.core.Commands;

import org.DayPrint.core.main;
import org.DayPrint.core.support;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetDay implements CommandExecutor {

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

            Player pl;
            if(sender instanceof Player)
            {
                pl = (Player) sender;
            }
            else
            {
                pl = null;
            }

            if(args.length != 1) {
                if(pl != null)
                    support.playerPrint("Command","Wrong usage of command",pl);
                return false;
            }
            else
            {
                String argument = args[0];
                int value;
                try
                {
                    value = Integer.parseInt(argument);
                } catch (NumberFormatException e) {
                    value = 0;
                }


                main.getConfigHandler().setConfigEntry("days",value);

                if(pl != null)
                    support.playerPrint("Command", "Set Day Value to " + value + "!", pl);
            }
            return true;
        }
}
