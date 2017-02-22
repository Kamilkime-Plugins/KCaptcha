package io.github.kamilkime.kcaptcha.bossbar.versioned;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;

import io.github.kamilkime.kcaptcha.bossbar.KBoss;
import io.github.kamilkime.kcaptcha.bossbar.KBossBarUtils;
import io.github.kamilkime.kcaptcha.enums.BossType;

public class v1_7_R4_plib extends KBoss {

	private WrappedDataWatcher dataWatcher;
	
	public v1_7_R4_plib(String name, Location location, float startPercent, Object color, Object style, BossType bossType) {
		super(name, location, startPercent, color, style, bossType);
	}

	@Override
	public void sendMetaPacket(Player p) {
		PacketContainer pc = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
		pc.getIntegers().write(0, (Integer) this.boss);
		pc.getWatchableCollectionModifier().write(0, getDataWatcher().getWatchableObjects());
		sendPacket(p, pc);
	}

	@Override
	public void sendSpawnPacket(Player p) {
		PacketContainer pc = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY_LIVING);
		this.boss = KBossBarUtils.getRandomEntityID();
		
		pc.getIntegers().write(0, (int) this.boss);
		pc.getIntegers().write(1, this.bossType.entityNumber);
		pc.getIntegers().write(2, (int) (this.location.getX() * 32.0D));
		pc.getIntegers().write(3, (int) (this.location.getY() * 32.0D));
		pc.getIntegers().write(4, (int) (this.location.getZ() * 32.0D));
		pc.getBytes().write(0, ((byte)(int)(this.location.getYaw() * 256.0F / 360.0F)));
		pc.getBytes().write(1, ((byte)(int)(this.location.getPitch() * 256.0F / 360.0F)));
        pc.getDataWatcherModifier().write(0, getDataWatcher());
        
		sendPacket(p, pc);
	}

	@Override
	public void sendDestroyPacket(Player p) {
		PacketContainer pc = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
		pc.getIntegerArrays().write(0, new int[]{(int) this.boss});
		sendPacket(p, pc);
	}

	@Override
	public void sendTeleportPacket(Player p, Location to) {
		PacketContainer pc = new PacketContainer(PacketType.Play.Server.ENTITY_TELEPORT);
		pc.getIntegers().write(0, (int) this.boss);
		pc.getIntegers().write(1, (int) (to.getX() * 32.0D));
		pc.getIntegers().write(2, (int) (to.getY() * 32.0D));
		pc.getIntegers().write(3, (int) (to.getZ() * 32.0D));
		pc.getBytes().write(0, ((byte)(int)(to.getYaw() * 256.0F / 360.0F)));
		pc.getBytes().write(1, ((byte)(int)(to.getPitch() * 256.0F / 360.0F)));
		sendPacket(p, pc);
	}

	@Override
	public void sendPacket(Player p, Object packet) {
		try {
			ProtocolLibrary.getProtocolManager().sendServerPacket(p, (PacketContainer) packet);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	private WrappedDataWatcher getDataWatcher() {
		if(dataWatcher == null) {
			dataWatcher = new WrappedDataWatcher();
			dataWatcher.setObject(0, (byte) 0x20);
			dataWatcher.setObject(10, this.name);
			dataWatcher.setObject(11, (byte) 1);
		}
		
		dataWatcher.setObject(6, this.hp);
		return dataWatcher;
	}
}