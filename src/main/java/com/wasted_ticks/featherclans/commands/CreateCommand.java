package com.wasted_ticks.featherclans.commands;

import com.wasted_ticks.featherclans.FeatherClans;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CreateCommand implements CommandExecutor {

    private final FeatherClans plugin;

    public CreateCommand(FeatherClans plugin) {
        this.plugin  = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player){

            Player player = (Player) sender;

            if (args.length != 2) {
                player.sendMessage("Error: Invalid usage, \"/clan create <tag>\", remember to hold your clan banner.");
                return false;
            }

            String tag = args[1];

            if(!tag.chars().allMatch(Character::isLetter) || tag.length() <= 5) {
                player.sendMessage("Error: <tag> must be alphabetical and must not be longer than 5 characters.");
                return false;
            }

            ItemStack stack = player.getInventory().getItemInMainHand();

            if (!stack.getType().name().contains("BANNER")) {
                player.sendMessage("Error: Unable to create clan, you must be holding a banner that will be used to represent your clan.");
                return false;
            }

            boolean inClan = plugin.getClanManager().isPlayerInClan(player);

            if(inClan) {
                player.sendMessage("Error: Unable to create clan, you are currently a member of a clan.");
            }

            //check if has balance available to create.

            plugin.getClanManager().createClan(player, stack, tag);

        }

        return true;
    }

}