package io.github.kamilkime.kcaptcha.title.versioned;

import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import io.github.kamilkime.kcaptcha.title.KTitleSender;
import net.minecraft.server.v1_10_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_10_R1.Packet;
import net.minecraft.server.v1_10_R1.PacketPlayOutChat;
import net.minecraft.server.v1_10_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_10_R1.PacketPlayOutTitle.EnumTitleAction;

public class v1_10_R1 implements KTitleSender {

	@Override
	public void sendTitle(Player p, String text, String textLocation, int fadeIn, int stay, int fadeOut) {
		Packet<?> pc = new PacketPlayOutTitle(EnumTitleAction.valueOf(textLocation), ChatSerializer.a("{\"text\":\"" + text + "\"}"), fadeIn, stay, fadeOut);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(pc);
	}

	@Override
	public void sendActionBar(Player p, String text, int fadeIn, int stay, int fadeOut) {
		Packet<?> pc = new PacketPlayOutChat(ChatSerializer.a("{\"text\":\"" + text + "\"}"), (byte) 2);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(pc);
	}
}