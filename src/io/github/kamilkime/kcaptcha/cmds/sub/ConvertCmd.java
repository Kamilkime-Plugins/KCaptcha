package io.github.kamilkime.kcaptcha.cmds.sub;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.kamilkime.kcaptcha.Main;
import io.github.kamilkime.kcaptcha.cmds.KCommand;
import io.github.kamilkime.kcaptcha.data.DataManager;
import io.github.kamilkime.kcaptcha.utils.StringUtils;

public class ConvertCmd implements KCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(!(sender instanceof ConsoleCommandSender)){
			sender.sendMessage(StringUtils.color("&7&l[KCaptcha] &cThis command is only for console!"));
			return;
		}
		
		if(args.length < 2){
			for(String msg : DataManager.getInst().mainMessageWrongSyntax) {
				sender.sendMessage(msg.replace("{COMMAND}", "/kcaptcha convert <mode>"));
			}
			return;
		}
		
		if(args[1].equalsIgnoreCase("pre15")) {
			File cfg = new File(Main.getInst().getDataFolder(), "config.yml");
			File msg = new File(Main.getInst().getDataFolder(), "messages.yml");
			
			if(!cfg.exists() && !msg.exists()) {
				sender.sendMessage(StringUtils.color("&7&l[KCaptcha] &cThere are no files to convert!"));
				return;
			}
			
			sender.sendMessage(StringUtils.color("&7&l[KCaptcha] &c&lRemember - this command only changes section names!"));
			sender.sendMessage(StringUtils.color("&7&l[KCaptcha] &c&lIt DOES NOT move those sections to correct files!"));
			sender.sendMessage(StringUtils.color("&7&l[KCaptcha] &c&lIt just eases data copying between files :)"));
			
			if(cfg.exists()) {
				YamlConfiguration cfgYml = YamlConfiguration.loadConfiguration(cfg);
				cfgYml.set("enableKBossBar", cfgYml.get("enableBarMessage"));
				cfgYml.set("barDisplayTime", cfgYml.get("bossBarTime"));
				cfgYml.set("enableKTitle", cfgYml.getBoolean("enableTitle") || cfgYml.getBoolean(("enableSubtitle")));
				cfgYml.set("fadeProperties", Arrays.asList(cfgYml.getInt("titleFadeIn"), cfgYml.getInt("titleStay"), cfgYml.getInt("titleFadeOut")));
				
				List<String> functionList = new ArrayList<String>();
				if(cfgYml.getBoolean("enableTitle")) functionList.add("TITLE");
				if(cfgYml.getBoolean("enableSubtitle")) functionList.add("SUBTITLE");
				cfgYml.set("enabledFunctions", functionList);
				
				cfgYml.set("configVersion", null);
				cfgYml.set("bossBarTime", null);
				cfgYml.set("bypassPermission", null);
				cfgYml.set("reloadPermission", null);
				cfgYml.set("infoPermission", null);
				cfgYml.set("enableBarMessage", null);
				cfgYml.set("enableTitle", null);
				cfgYml.set("enableSubtitle", null);
				cfgYml.set("titleFadeIn", null);
				cfgYml.set("titleStay", null);
				cfgYml.set("titleFadeOut", null);
				cfgYml.set("subtitleFadeIn", null);
				cfgYml.set("subtitleStay", null);
				cfgYml.set("subtitleFadeOut", null);
				
				try {
					cfgYml.save(cfg);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				sender.sendMessage(StringUtils.color("&7&l[KCaptcha] &a&lOld config file contents have been converted!"));
			}
			
			if(msg.exists()) {
				YamlConfiguration msgYml = YamlConfiguration.loadConfiguration(msg);
				msgYml.set("chatMessageCompleteCaptcha", msgYml.get("msgYouHaveToCompleteCaptcha"));
				msgYml.set("chatMessageCaptchaCompleted", msgYml.get("msgCaptchaComplete"));
				msgYml.set("mainMessageReloadCommand", msgYml.get("msgReloadCommand"));
				msgYml.set("mainMessageInfoCommand", msgYml.get("msgInfoCommand"));
				msgYml.set("mainMessageHelpCommand", msgYml.get("msgHelpCommand"));
				msgYml.set("mainMessageNoPermission", msgYml.get("msgNoPermission"));
				msgYml.set("mainMessageNoSuchPlayer", msgYml.get("msgNoSuchPlayer"));
				msgYml.set("mainMessageWrongSyntax", msgYml.get("msgWrongSyntax"));
				msgYml.set("mainMessageFeatureNotEnabled", msgYml.get("msgFeatureNotEnabled"));
				msgYml.set("barMessageCompleteCaptcha", msgYml.get("barYouHaveToCompleteCaptcha"));
				msgYml.set("barMessageCaptchaCompleted", msgYml.get("barCaptchaComplete"));
				msgYml.set("titleMessageCompleteCaptcha", msgYml.get("titleYouHaveToCompleteCaptcha"));
				msgYml.set("titleMessageCaptchaCompleted", msgYml.get("titleCaptchaComplete"));
				msgYml.set("subtitleMessageCompleteCaptcha", msgYml.get("subtitleYouHaveToCompleteCaptcha"));
				msgYml.set("subtitleMessageCaptchaCompleted", msgYml.get("subtitleCaptchaComplete"));
				
				msgYml.set("messagesVersion", null);
				msgYml.set("msgYouHaveToCompleteCaptcha", null);
				msgYml.set("msgCaptchaComplete", null);
				msgYml.set("msgReloadCommand", null);
				msgYml.set("msgInfoCommand", null);
				msgYml.set("msgHelpCommand", null);
				msgYml.set("msgNoPermission", null);
				msgYml.set("msgNoSuchPlayer", null);
				msgYml.set("msgWrongSyntax", null);
				msgYml.set("msgFeatureNotEnabled", null);
				msgYml.set("barYouHaveToCompleteCaptcha", null);
				msgYml.set("barCaptchaComplete", null);
				msgYml.set("titleYouHaveToCompleteCaptcha", null);
				msgYml.set("titleCaptchaComplete", null);
				msgYml.set("subtitleYouHaveToCompleteCaptcha", null);
				msgYml.set("subtitleCaptchaComplete", null);
				
				try {
					msgYml.save(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				sender.sendMessage(StringUtils.color("&7&l[KCaptcha] &a&lOld message file contents have been converted!"));
			}
			
		} else {
			sender.sendMessage(StringUtils.color("&7&l[KCaptcha] &cThere is no such mode as " + args[1] + "!"));
			sender.sendMessage(StringUtils.color("&7&l[KCaptcha] &cAvailable modes: pre15"));
		}
	}
}