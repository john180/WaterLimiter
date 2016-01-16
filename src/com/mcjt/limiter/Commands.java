package com.mcjt.limiter;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {
	private Main p;
	public Commands(Main main){
		this.p=main;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,String label, String[] args) {
		if(args.length==1){
			if(args[0].equalsIgnoreCase("reload")){
				if(sender.hasPermission("WaterLimiter.reload")){
					p.reloadConfig();
					p.map.clear();
					p.map1.clear();
					p.loadconfig();
					sender.sendMessage(ChatColor.GREEN+"插件已重载");
					return true;
				}else{
					sender.sendMessage(ChatColor.RED+"无权限使用此指令");
					return true;
				}
			}else{
				sender.sendMessage(ChatColor.RED+"参数错误");
				sender.sendMessage(ChatColor.GREEN+"/wlimit reload");
				return true;
			}
		}else{
			sender.sendMessage(ChatColor.RED+"参数错误");
			return true;
		}
	}
}
