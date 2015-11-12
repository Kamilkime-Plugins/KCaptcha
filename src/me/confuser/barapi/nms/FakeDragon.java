package me.confuser.barapi.nms;

import me.confuser.barapi.Util;

import org.bukkit.Location;

public abstract class FakeDragon {
	private float maxHealth = 200;
	private int x;
	private int y;
	private int z;

	private int pitch = 0;
	private int yaw = 0;
	private byte xvel = 0;
	private byte yvel = 0;
	private byte zvel = 0;
	public float health = 0;
	private boolean visible = false;
	public String name;
	private Object world;

	public FakeDragon(String name, Location loc, int percent) {
		this.name = name;
		this.x = loc.getBlockX();
		this.y = loc.getBlockY();
		this.z = loc.getBlockZ();
		this.health = percent / 100F * maxHealth;
		this.world = Util.getHandle(loc.getWorld());
	}

	public FakeDragon(String name, Location loc) {
		this.name = name;
		this.x = loc.getBlockX();
		this.y = loc.getBlockY();
		this.z = loc.getBlockZ();
		this.world = Util.getHandle(loc.getWorld());
	}

	public float getMaxHealth() {
		return maxHealth;
	}

	public void setHealth(int percent) {
		this.health = percent / 100F * maxHealth;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public int getPitch() {
		return pitch;
	}

	public void setPitch(int pitch) {
		this.pitch = pitch;
	}

	public int getYaw() {
		return yaw;
	}

	public void setYaw(int yaw) {
		this.yaw = yaw;
	}

	public byte getXvel() {
		return xvel;
	}

	public void setXvel(byte xvel) {
		this.xvel = xvel;
	}

	public byte getYvel() {
		return yvel;
	}

	public void setYvel(byte yvel) {
		this.yvel = yvel;
	}

	public byte getZvel() {
		return zvel;
	}

	public void setZvel(byte zvel) {
		this.zvel = zvel;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Object getWorld() {
		return world;
	}

	public void setWorld(Object world) {
		this.world = world;
	}
	
	public void setMaxHealth(float max) {
		maxHealth = max;
	}

	public abstract Object getSpawnPacket();

	public abstract Object getDestroyPacket();

	public abstract Object getMetaPacket(Object watcher);

	public abstract Object getTeleportPacket(Location loc);

	public abstract Object getWatcher();
}
