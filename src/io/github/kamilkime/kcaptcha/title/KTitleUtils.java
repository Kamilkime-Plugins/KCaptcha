package io.github.kamilkime.kcaptcha.title;

import io.github.kamilkime.kcaptcha.Main;
import io.github.kamilkime.kcaptcha.utils.ReflectionUtils;

public class KTitleUtils {

	private static final String packageName = "io.github.kamilkime.kcaptcha.title.versioned.";
	private static KTitleSender titleSender;
	
	public static boolean findClass() {
		try {
			titleSender = (KTitleSender) ReflectionUtils.getClass(packageName + ReflectionUtils.VERSION + (Main.isModded() ? "_plib" : "")).newInstance();
			return true;
		} catch(Exception e) {}
		
		return false;
	}
	
	public static KTitleSender getTitleSender() {
		return titleSender;
	}
	
	public static String getInitializedClassName() {
		return (titleSender == null ? "" : titleSender.getClass().getSimpleName());
	}
}