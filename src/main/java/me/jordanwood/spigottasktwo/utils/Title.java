package me.jordanwood.spigottasktwo.utils;

import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Title {
    public static boolean createTitle(Player p, String message, @Nullable String subMessage){
        IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");


        PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
        PacketPlayOutTitle subTitle = null;

        if (subMessage != null){
            IChatBaseComponent chatSubTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subMessage + "\"}");
            subTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatSubTitle);
        }

        PacketPlayOutTitle length = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, 40, 100, 40);

        EntityPlayer cp = ((CraftPlayer) p).getHandle();
        PlayerConnection pc = cp.playerConnection;

        pc.sendPacket(title);

        if (subTitle != null) {
            pc.sendPacket(subTitle);
        }

        pc.sendPacket(length);
        return true;
    }
}

