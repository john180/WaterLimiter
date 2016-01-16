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
					sender.sendMessage(ChatColor.GREEN+"���������");
					return true;
				}else{
					sender.sendMessage(ChatColor.RED+"��Ȩ��ʹ�ô�ָ��");
					return true;
				}
			}else{
				sender.sendMessage(ChatColor.RED+"��������");
				sender.sendMessage(ChatColor.GREEN+"/wlimit reload");
				return true;
			}
		}else{
			sender.sendMessage(ChatColor.RED+"��������");
			return true;
		}
	}
}
