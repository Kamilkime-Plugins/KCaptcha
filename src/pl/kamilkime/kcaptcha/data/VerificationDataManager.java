package pl.kamilkime.kcaptcha.data;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;

import pl.kamilkime.kcaptcha.objects.VerifiedUser;
import pl.kamilkime.kcaptcha.objects.utils.VerificationUtils;

/*
 * This class is used ONLY if uuid remembering is enabled
 */

public class VerificationDataManager {

	private static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z");
	
	public static void load(File mainDir){
		File f = new File(mainDir, "data.yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
		for(String uuid : yml.getKeys(false)){
			try {
				VerifiedUser.createUser(UUID.fromString(uuid), yml.getString(uuid + ".name"), yml.getString(uuid + ".ip"), sdf.parse(yml.getString(uuid + ".date")));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void save(File mainDir){
		File f = new File(mainDir, "data.yml");
		YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
		for(VerifiedUser u : VerificationUtils.getUsers()){
			String uuid = u.getUUID().toString();
			yml.set(uuid + ".name", u.getName());
			yml.set(uuid + ".ip", u.getIp());
			yml.set(uuid + ".date", sdf.format(u.getDate()));
		}
		try {
			yml.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}