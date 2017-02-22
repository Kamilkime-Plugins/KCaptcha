package io.github.kamilkime.kcaptcha.bossbar;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import io.github.kamilkime.kcaptcha.Main;
import io.github.kamilkime.kcaptcha.bossbar.versioned.v1_7_R4_hack;
import io.github.kamilkime.kcaptcha.bossbar.versioned.vBukkitAPI;
import io.github.kamilkime.kcaptcha.data.DataManager;
import io.github.kamilkime.kcaptcha.enums.BossType;
import io.github.kamilkime.kcaptcha.utils.ReflectionUtils;
import io.github.kamilkime.kcaptcha.utils.ReflectionUtils.ConstructorInvoker;

public class KBossBarUtils {

	private static final Random rand = new Random();
	private static final String packageName = "io.github.kamilkime.kcaptcha.bossbar.versioned.";
	private static boolean usingBukkit;
	private static Class<?> barClass;
	private static ConstructorInvoker barConstructor;
	
	public static boolean findClass() {
		try {
			ReflectionUtils.getClass("org.bukkit.boss.BossBar");
			barClass = vBukkitAPI.class;
			usingBukkit = true;
			return true;
		} catch(Exception e) {}
		
		if(ReflectionUtils.VERSION.equals("v1_7_R4")) {
			try {
				ReflectionUtils.getClass("net.minecraft.server.v1_7_R4.PacketPlayOutEntityTeleport").getConstructor(int.class, int.class, int.class, int.class, byte.class, byte.class, boolean.class, boolean.class);
				barClass = v1_7_R4_hack.class;
				return true;
			} catch (Exception e) {}
		}
		
		try {
			barClass = ReflectionUtils.getClass(packageName + ReflectionUtils.VERSION + (Main.isModded() ? "_plib" : ""));
			return true;
		} catch(Exception e) {}
		
		return false;
	}
	
	public static void createConstructor() {
		barConstructor = ReflectionUtils.getConstructor(barClass, String.class, Location.class, float.class, Object.class, Object.class, BossType.class);
	}
	
	public static Location getDragonLocation(Location playerLocation) {
		return playerLocation.add(playerLocation.getDirection().multiply(Bukkit.getServer().getViewDistance()*8));
	}
	
	public static KBoss newBoss(String name, Location location, float startPercent, Object color, Object style) {
		return (KBoss) barConstructor.invoke(name, location, startPercent, color, style, DataManager.getInst().bossType);
	}
	
	public static String getInitializedClassName() {
		return (barClass == null ? "" : barClass.getSimpleName());
	}
	
	public static int getRandomEntityID() {
		return rand.nextInt(10000) + 10000;
	}
	
	public static Object getColor(String color) {
		if(!usingBukkit) return null;
		return vBukkitAPI.getColor(color);
	}
	
	public static Object getStyle(String style) {
		if(!usingBukkit) return null;
		return vBukkitAPI.getStyle(style);
	}
}