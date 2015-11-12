package pl.kamilkime.kcaptcha.data;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

public class MessagesLoader {

	public static Map<String, Object> load(String lang, File mainDir){
		Map<String, Object> output = new HashMap<String, Object>();
		File f = new File(mainDir, "messages_" + lang.toLowerCase() + ".yml");
		if(!f.exists()) f = new File(mainDir, "messages_en.yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
		
		for(String section : yml.getKeys(false)){
			output.put(section, yml.get(section));
		}
		return output;
	}
}