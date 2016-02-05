package com.gmail.kamilkime.kcaptcha.title;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gmail.kamilkime.kcaptcha.data.FileUtils;
import com.gmail.kamilkime.kcaptcha.data.StringUtils;

public class TitleUtils {

	private static boolean isEnabled;
	private static Method getHandle;
	private static Method sendPacket;
	private static Method valueOf;
	private static Method a;
	private static Constructor<?> titleConstructor;
	private static Class<?> enumTitleAction;
	private static Field playerConnection;
	private static String versionString;
	private static String versionEnding;
	
	public static void load(){
		try{
			enumTitleAction = (versionEnding.equals("R1") ? getNmsClass("EnumTitleAction") : getNmsClass("PacketPlayOutTitle$EnumTitleAction"));
			valueOf = enumTitleAction.getMethod("valueOf", String.class);
			getHandle = Class.forName("org.bukkit.craftbukkit." + versionString + "entity.CraftPlayer").getMethod("getHandle");
			titleConstructor = getNmsClass("PacketPlayOutTitle").getConstructor(enumTitleAction, getNmsClass("IChatBaseComponent"), int.class, int.class, int.class);
			
			Class<?> serializer = (versionEnding.equals("R1") ? getNmsClass("ChatSerializer") : getNmsClass("IChatBaseComponent$ChatSerializer"));
			a = serializer.getDeclaredMethod("a", String.class);
			
			Class<?> entityPlayer = getNmsClass("EntityPlayer");
			playerConnection = entityPlayer.getField("playerConnection");
			sendPacket = getNmsClass("PlayerConnection").getMethod("sendPacket", getNmsClass("Packet"));
		} catch(ClassNotFoundException | NoSuchFieldException | SecurityException | NoSuchMethodException e){
			e.printStackTrace();
		}
	}
	
	public static boolean canUse18(){
		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		String[] toCheck = version.split("_");
		if(toCheck[1].equalsIgnoreCase("8")){
			isEnabled = true;
			versionString = version + ".";
			versionEnding = toCheck[2];
			return true;
		}
		isEnabled = false;
		return false;
	}
	
	private static Class<?> getNmsClass(String className){
		String name = "net.minecraft.server." + versionString + className;
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void sendTitlePacket(Player toSend, Object packet){
		try{
			Object entityPlayer = getHandle.invoke(toSend);
			Object pConnection = playerConnection.get(entityPlayer);
			sendPacket.invoke(pConnection, packet);
			
		} catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
			try {
				Bukkit.getConsoleSender().sendMessage(StringUtils.color("&7&l[KCaptcha] &c&lCannot send title packet! Error log was saved to &7&lerrorLogs &c&lfolder in the "
						+ "plugin folder as &7&l" + FileUtils.createErrorLog(e.getStackTrace()) + " &c&lfile!"));
				Bukkit.getConsoleSender().sendMessage(StringUtils.color("&7&l[KCaptcha] &c&lSend this file to plugin developer via &7&lspigotmc.org &c&las fast as possible!"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public static Object createTitlePacket(String captcha, String type, String text, int fadeIn, int stay, int fadeOut){
		try{
			Object titleType = valueOf.invoke(enumTitleAction, type.toUpperCase());
			return titleConstructor.newInstance(titleType, titleStringToJSON(text, captcha), fadeIn, stay, fadeOut);
		} catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e){
			try {
				Bukkit.getConsoleSender().sendMessage(StringUtils.color("&7&l[KCaptcha] &c&lCannot create title packet! Error log was saved to &7&lerrorLogs &c&lfolder in the "
						+ "plugin folder as &7&l" + FileUtils.createErrorLog(e.getStackTrace()) + " &c&lfile!"));
				Bukkit.getConsoleSender().sendMessage(StringUtils.color("&7&l[KCaptcha] &c&lSend this file to plugin developer via &7&lspigotmc.org &c&las fast as possible!"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
	
	private static Object titleStringToJSON(String text, String captcha){
		try{
			String toJSON = "{\"text\":\"" + ChatColor.translateAlternateColorCodes('&', text).replace("{CAPTCHA}", captcha).replace("{CODE}", captcha) + "\"}";
			Object returned = a.invoke(null, toJSON);
			return returned;
		}  catch(IllegalArgumentException | InvocationTargetException | IllegalAccessException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean canUseTitles(){
		return isEnabled;
	}
}