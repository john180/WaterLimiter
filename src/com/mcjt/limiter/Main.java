package com.mcjt.limiter;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;

public class Main extends JavaPlugin implements Listener{
	HashMap<String, Boolean> map;
	HashMap<String, Integer> map1;
	public void onEnable()
	{
		map = new HashMap<String,Boolean>();
		map1 = new HashMap<String, Integer>();
		saveDefaultConfig();
		checkconfig();
		loadconfig();
		getServer().getPluginManager().registerEvents(this, this);
		this.getCommand("wlimit").setExecutor(new Commands(this));
		getLogger().info("插件已加载");
	}
	public void ondisable(){
		getLogger().info("插件已卸载");
	}
	public void createconfig(World w){
		String wn=w.getName();
		getConfig().set(wn+".residence.LAVA", true);
		getConfig().set(wn+".residence.WATER", true);
		getConfig().set(wn+".world.LAVA", true);
		getConfig().set(wn+".world.WATER", true);
		getConfig().set(wn+".world.high.LAVA", 0);
		getConfig().set(wn+".world.high.WATER", 0);
		saveConfig(); 
	}
	public void checkconfig(){
		for(World w : getServer().getWorlds()){
			if(!getConfig().contains(w.getName()+".residence")){
				createconfig(w);
			}
		}
	}
	public void loadconfig(){
		for(World world : getServer().getWorlds()){
			String wn=world.getName();
			map.put(wn+"rl", getConfig().getBoolean(wn+".residence.LAVA"));
			map.put(wn+"rw", getConfig().getBoolean(wn+".residence.WATER"));
			map.put(wn+"wl", getConfig().getBoolean(wn+".world.LAVA"));
			map.put(wn+"ww", getConfig().getBoolean(wn+".world.WATER"));
			map1.put(wn+"wl", getConfig().getInt(wn+".world.high.LAVA"));
			map1.put(wn+"ww", getConfig().getInt(wn+".world.high.WATER"));
		}
	}
	@EventHandler(priority=EventPriority.HIGHEST,ignoreCancelled=true)
	public void onBlockFromToEvent (BlockFromToEvent event) {
		Material material = event.getBlock().getType();
		if ((material == Material.WATER)||(material == Material.STATIONARY_WATER)){
			Location loc= event.getToBlock().getLocation();
			String wn = loc.getWorld().getName();
			ClaimedResidence res = Residence.getResidenceManager().getByLoc(loc);
			if(res!=null){
				event.setCancelled(map.get(wn+"rw"));
				return;
			}else{
				if(map1.get(wn+"ww")>loc.getBlockY()){
					event.setCancelled(false);
					return;
				}else{
					event.setCancelled(map.get(wn+"ww"));
					return;
				}
			}				 
		}
		if((material == Material.LAVA)||(material==Material.STATIONARY_LAVA)){
			Location loc= event.getToBlock().getLocation();
			String wn = loc.getWorld().getName();
			ClaimedResidence res = Residence.getResidenceManager().getByLoc(loc);
			if(res != null){	 
				event.setCancelled(map.get(wn+"rl"));   
			} else{
				if(map1.get(wn+"wl")>loc.getBlockY()){
					event.setCancelled(false);
				}else{
					event.setCancelled(map.get(wn+"wl"));
				}
			}
		}	  
	}
}