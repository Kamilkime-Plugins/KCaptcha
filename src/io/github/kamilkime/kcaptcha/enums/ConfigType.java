package io.github.kamilkime.kcaptcha.enums;

public enum ConfigType {
	bar(1),
	chat(1),
	main(1),
	title(1);
	
	private int version;
	
	ConfigType(int version) {
		this.version = version;
	}
	
	public int getVersion() {
		return this.version;
	}
}