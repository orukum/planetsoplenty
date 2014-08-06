package foodisgood_orukum.mods.pop.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import micdoodle8.mods.galacticraft.core.network.GCCorePacketHandlerClient.EnumPacketClient;
import foodisgood_orukum.mods.pop.network.POPPacketHandlerServer.EnumPacketServer;
import net.minecraft.network.packet.Packet250CustomPayload;

public class POPPacketUtils {
    public static Packet250CustomPayload createPacket(String channel, EnumPacketClient packetType, Object[] input) {
        if (packetType == null)
            return null;
        return createPacket(channel, packetType.getIndex(), input);
    }

    public static Packet250CustomPayload createPacket(String channel, EnumPacketServer packetType, Object[] input) {
        if (packetType == null)
            return null;
        return createPacket(channel, packetType.getIndex(), input);
    }

    public static Packet250CustomPayload createPacket(String channel, int packetID, Object[] input) {
        final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        final DataOutputStream data = new DataOutputStream(bytes);
        try {
            data.writeInt(packetID);
            if (input != null)
                for (final Object obj : input)
                    writeObjectToStream(obj, data);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        final Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = channel;
        packet.data = bytes.toByteArray();
        packet.length = packet.data.length;

        return packet;
    }

    public static Object[] readPacketData(DataInputStream data, Class<?>[] packetDataTypes) {
        final List<Object> result = new ArrayList<Object>();
        try {
            for (Class<?> curClass : packetDataTypes)
                result.add(readObjectFromStream(data, curClass));
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return result.toArray();
    }

    private static void writeObjectToStream(Object o, DataOutputStream data) throws IOException {
        if (o instanceof Boolean)
            data.writeBoolean((Boolean) o);
        else if (o instanceof Byte)
            data.writeByte((Byte) o);
        else if (o instanceof Integer)
            data.writeInt((Integer) o);
        else if (o instanceof String)
            data.writeUTF((String) o);
        else if (o instanceof Double)
            data.writeDouble((Double) o);
        else if (o instanceof Float)
            data.writeFloat((Float) o);
        else if (o instanceof Long)
            data.writeLong((Long) o);
        else if (o instanceof Short)
            data.writeShort((Short) o);
    }

    private static Object readObjectFromStream(DataInputStream data, Class<?> curClass) throws IOException {
        if (curClass.equals(Boolean.class))
            return data.readBoolean();
        else if (curClass.equals(Byte.class))
            return data.readByte();
        else if (curClass.equals(Integer.class))
            return data.readInt();
        else if (curClass.equals(String.class))
            return data.readUTF();
        else if (curClass.equals(Double.class))
            return data.readDouble();
        else if (curClass.equals(Float.class))
            return data.readFloat();
        else if (curClass.equals(Long.class))
            return data.readLong();
        else if (curClass.equals(Short.class))
            return data.readShort();
        return null;
    }

    public static int readPacketID(DataInputStream data)
    {
        int result = -1;
        try {
            result = data.readInt();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
