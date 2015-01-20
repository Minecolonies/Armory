package com.Orion.Armory.Network.Messages;
/*
 *   MessageTileEntityArmory
 *   Created by: Orion
 *   Created on: 19-1-2015
 */

import com.Orion.Armory.Common.TileEntity.TileEntityArmory;
import com.Orion.Armory.Util.Core.ForgeDirectionHelper;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class MessageTileEntityArmory
{
    public String iName;
    public ForgeDirection iCurrentDirection;
    public int xCoord, yCoord, zCoord;

    public MessageTileEntityArmory(TileEntityArmory pEntity)
    {
        this.iName = pEntity.getDisplayName();
        this.iCurrentDirection = pEntity.getDirection();
        this.xCoord = pEntity.xCoord;
        this.yCoord = pEntity.yCoord;
        this.zCoord = pEntity.zCoord;
    }

    public MessageTileEntityArmory() {}

    public void fromBytes(ByteBuf buf)
    {
        xCoord = buf.readInt();
        yCoord = buf.readInt();
        zCoord = buf.readInt();

        iName = ByteBufUtils.readUTF8String(buf);
        iCurrentDirection = ForgeDirection.getOrientation(buf.readInt());
    }

    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(xCoord);
        buf.writeInt(yCoord);
        buf.writeInt(zCoord);

        ByteBufUtils.writeUTF8String(buf, iName);

        buf.writeInt(ForgeDirectionHelper.ConvertToInt(iCurrentDirection));
    }

    public IMessage onMessage(MessageTileEntityArmory message, MessageContext ctx)
    {
        TileEntity tEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.xCoord, message.yCoord, message.zCoord);
        if (tEntity instanceof TileEntityArmory)
        {
            ((TileEntityArmory) tEntity).setDisplayName(message.iName);
            ((TileEntityArmory) tEntity).setDirection(message.iCurrentDirection);
        }

        FMLClientHandler.instance().getClient().theWorld.func_147451_t(message.xCoord, message.yCoord, message.zCoord);

        return null;
    }
}