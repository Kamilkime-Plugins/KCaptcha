package pl.kamilkime.kcaptcha.title;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TitleUtils {

	private static Method getHandle;
	private static Method sendPacket;
	private static Method valueOf;
	private static Method a;
	private static Constructor<?> titleConstructor;
	private static Class<?> titleActionClass;
	private static Class<?> chatComp;
	private static Class<?> titleClass;
	private static Field pConnection;
	private static boolean isEnabled;
	private static String versionString;
	private static String versionEnding;
	
	public static void load(){
		try{
			chatComp = getNmsClass("IChatBaseComponent");
			titleActionClass = (versionEnding.equals("R1") ? getNmsClass("EnumTitleAction") : getNmsClass("PacketPlayOutTitle$EnumTitleAction"));
			titleClass = getNmsClass("PacketPlayOutTitle");
			valueOf = titleActionClass.getMethod("valueOf", String.class);
			getHandle = Class.forName("org.bukkit.craftbukkit." + versionString + "entity.CraftPlayer").getMethod("getHandle");
			titleConstructor = titleClass.getConstructor(titleActionClass, chatComp, int.class, int.class, int.class);
			
			Class<?> serializer = (versionEnding.equals("R1") ? getNmsClass("ChatSerializer") : getNmsClass("IChatBaseComponent$ChatSerializer"));
			a = serializer.getDeclaredMethod("a", String.class);
			
			Class<?> entityPlayer = getNmsClass("EntityPlayer");
			pConnection = entityPlayer.getField("playerConnection");
			sendPacket = getNmsClass("PlayerConnection").getMethod("sendPacket", getNmsClass("Packet"));
		} catch(Exception e){
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
			Object playerConnection = pConnection.get(entityPlayer);
			sendPacket.invoke(playerConnection, packet);
			
		} catch(Exception e){
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&l[KCaptcha] &4&lCannot send title packet! Tell developer about this problem!"));
			e.printStackTrace();
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&l[KCaptcha] &4&lCannot send title packet! Tell developer about this problem!"));
		}
	}
	
	public static Object createTitlePacket(String captcha, String type, String text, int fadeIn, int stay, int fadeOut){
		try{
			Object titleType = valueOf.invoke(titleActionClass, type.toUpperCase());
			return titleConstructor.newInstance(titleType, titleStringToJSON(text, captcha), fadeIn, stay, fadeOut);
		} catch(Exception e){
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&l[KCaptcha] &4&lCannot create title packet! Tell developer about this problem!"));
			e.printStackTrace();
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&l[KCaptcha] &4&lCannot create title packet! Tell developer about this problem!"));
		}
		return null;
	}
	
	private static Object titleStringToJSON(String text, String captcha){
		try{
			String toJSON = "{\"text\":\"" + ChatColor.translateAlternateColorCodes('&', text).replace("{CAPTCHA}", captcha).replace("{CODE}", captcha) + "\"}";
			Object returned = a.invoke(null, toJSON);
			return returned;
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean canUseTitles(){
		return isEnabled;
	}
}