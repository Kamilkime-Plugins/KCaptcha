package io.github.kamilkime.kcaptcha.bossbar;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.kamilkime.kcaptcha.enums.BossType;

public abstract class KBoss {

	public float hp;
	public Object boss;
	public String name;
	public Object color;
	public Object style;
	public Location location;
	public BossType bossType;
	
	public KBoss(String name, Location location, float startPercent, Object color, Object style, BossType bossType) {
		this.name = name;
		this.location = location;
		this.bossType = bossType;
		this.hp = startPercent / 100 * this.bossType.maxHP;
		this.color = color;
		this.style = style;
	}
	
	public abstract void sendMetaPacket(Player p);
	public abstract void sendSpawnPacket(Player p);
	public abstract void sendDestroyPacket(Player p);
	public abstract void sendPacket(Player p, Object packet);
	public abstract void sendTeleportPacket(Player p, Location to);
}