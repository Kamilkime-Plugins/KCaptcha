package pl.kamilkime.kcaptcha.data;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.kamilkime.kcaptcha.Main;
import pl.kamilkime.kcaptcha.objects.utils.StringManager;

public class DataManager {

	private DataManager(){
		inst = this;
	}
	
	private static DataManager inst;
	private File mainDir = Main.getInst().getDataFolder();
	private File cfgFile = new File(mainDir, "config.yml");
	private File dataFile = new File(mainDir, "data.yml");
	private Map<String, Object> configContents = new HashMap<String, Object>();
	private Map<String, Object> messages = new HashMap<String, Object>();
	
	public static DataManager getInst(){
		if(inst !=null) return inst;
		return new DataManager();
	}
	
	public void checkFiles(){
		if(!mainDir.exists()) mainDir.mkdir();
		if(!cfgFile.exists()) Main.getInst().saveResource("config.yml", true);
		if(!dataFile.exists()){
			try {
				dataFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(!new File(mainDir, "messages_en.yml").exists()) Main.getInst().saveResource("messages_en.yml", true);
		if(!new File(mainDir, "messages_pl.yml").exists()) Main.getInst().saveResource("messages_pl.yml", true);
	}
	
	public void load(){
		configContents = ConfigLoader.load();
		messages = MessagesLoader.load((String) configContents.get("lang"), mainDir);
		
		if(!configContents.containsKey("configVersion") || !((int) configContents.get("configVersion") == 2)){
			Main.getInst().saveResource("config.yml", true);
			configContents = ConfigLoader.load();
		}
		
		if(!messages.containsKey("messagesVersion") || !((int) messages.get("messagesVersion") == 2)){
			Main.getInst().saveResource("messages_en.yml", true);
			Main.getInst().saveResource("messages_pl.yml", true);
			messages = MessagesLoader.load("en", mainDir);
		}
		
		if((boolean) configContents.get("rememberUUIDs")) VerificationDataManager.load(mainDir);
	}
	
	public void save(){
		if((boolean) configContents.get("rememberUUIDs")) VerificationDataManager.save(mainDir);
	}
	
	public void reload(){
		Main.getInst().reloadConfig();
		configContents = ConfigLoader.load();
		messages = MessagesLoader.load((String) configContents.get("lang"), mainDir);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getChatMessage(String section, String captcha){
		return StringManager.createChatMessage((List<String>) messages.get(section), captcha);
	}
	
	public String getBarMessage(String section, String captcha){
		return StringManager.createBarMessage((String) messages.get(section), captcha);
	}
	
	public Object getPlainMessage(String section){
		return messages.get(section);
	}
	
	public Object getConfigContent(String section){
		return configContents.get(section);
	}
}