package io.github.kamilkime.kcaptcha.enums;

public enum BossType {
	DRAGON(200, 63),
	WITHER(300, 64);
	
	public int maxHP;
	public int entityNumber;
	
	BossType(int maxHP, int entityNumber) {
		this.maxHP = maxHP;
		this.entityNumber = entityNumber;
	}
}