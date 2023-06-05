package me.azhang.dndplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ModeCommands implements CommandExecutor {
    // [combat, mine, other]
    public static boolean[] modeStatus = new boolean[3];
    protected boolean modeActive = false;



    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            return true;
        }

        Player player = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("deactivate")){
            for(int i = 0; i < modeStatus.length; i++){
                modeStatus[i] = false;
            }
            modeActive = false;
            player.sendMessage("All modes have been deactivated.");
        }
        else if(cmd.getName().equalsIgnoreCase("combat")){
            if(modeActive){
                player.sendMessage("A mode is already activated and you can only have one active mode at once. Deactive all modes with the command /mode deactive");
            }
            else{
                player.sendMessage("Combat mode activated.");
                modeStatus[0] = true;
                modeActive = true;
            }
        }
        else if(cmd.getName().equalsIgnoreCase("mining")){
            if(modeActive){
                player.sendMessage("A mode is already activated and you can only have one active mode at once. Deactive all modes with the command /mode deactive");
            }
            else{
                player.sendMessage("Mining mode activated.");
                modeStatus[1] = true;
                modeActive = true;
            }
        }
        return true;
    }
}
