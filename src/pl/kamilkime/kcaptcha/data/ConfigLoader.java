package pl.kamilkime.kcaptcha.data;

import java.util.HashMap;
import java.util.Map;

import pl.kamilkime.kcaptcha.Main;

public class ConfigLoader {

	public static Map<String, Object> load(){
		Map<String, Object> output = new HashMap<String, Object>();
		for(String section : Main.getInst().getConfig().getKeys(false)){
			output.put(section, Main.getInst().getConfig().get(section));
		}
		return output;
	}
}