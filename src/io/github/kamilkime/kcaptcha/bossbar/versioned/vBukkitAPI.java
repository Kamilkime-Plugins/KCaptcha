package io.github.kamilkime.kcaptcha.bossbar.versioned;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import io.github.kamilkime.kcaptcha.bossbar.KBoss;
import io.github.kamilkime.kcaptcha.enums.BossType;

public class vBukkitAPI extends KBoss {

	public vBukkitAPI(String name, Location location, float startPercent, Object color, Object style, BossType bossType) {
		super(name, location, startPercent, color, style, bossType);
	}

	@Override
	public void sendMetaPacket(Player p) {
		((BossBar) this.boss).setProgress(this.hp / this.bossType.maxHP);
	}
	
	@Override
	public void sendSpawnPacket(Player p) {
		this.boss = Bukkit.createBossBar(this.name, (BarColor) this.color, (BarStyle) this.style, new BarFlag[]{});
		((BossBar) this.boss).setProgress(this.hp / this.bossType.maxHP);
		((BossBar) this.boss).addPlayer(p);
		((BossBar) this.boss).setVisible(true);
	}

	@Override
	public void sendDestroyPacket(Player p) {
		((BossBar) this.boss).removePlayer(p);
		((BossBar) this.boss).setVisible(false);
	}

	@Override
	public void sendTeleportPacket(Player p, Location to) {}

	@Override
	public void sendPacket(Player p, Object packet) {}
	
	public static Object getColor(String color) {
		return BarColor.valueOf(color.toUpperCase());
	}
	
	public static Object getStyle(String style) {
		return BarStyle.valueOf(style.toUpperCase());
	}
}