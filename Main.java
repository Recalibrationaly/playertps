package me.buryings.playertps;

import me.buryings.playertps.TPSCount;
import me.buryings.playertps.placeholders.TPSHook;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class Main extends JavaPlugin implements CommandExecutor, Listener {
    private String message;

    @SuppressWarnings("deprecation")
	public void onEnable() {
        this.getCommand("ptps").setExecutor((CommandExecutor)this);
        this.message = this.getConfig().getString(ChatColor.translateAlternateColorCodes((char)'&', (String)"tps-message"));
        this.loadConfig();
        Bukkit.getScheduler().scheduleAsyncRepeatingTask((Plugin)this, (Runnable)new TPSCount(), 100L, 1L);
        
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceHolderAPI")) {
        	new TPSHook().register();
        	Bukkit.getPluginManager().registerEvents(this, this);
        }
    }

    private void loadConfig() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ptps")) {
            Player player = (Player)sender;
            double tps = TPSCount.getTPS();
            double readableTPS = (double)Math.round(tps * 100.0) / 100.0;
		if (player.hasPermission("playertps.viewtps"))
            player.sendMessage(ChatColor.translateAlternateColorCodes((char)'&', (String)this.message.replace("%tps%", String.valueOf(readableTPS))));
        }
        return true;
    }
}
