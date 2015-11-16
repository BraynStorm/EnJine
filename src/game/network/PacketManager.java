package game.network;

import game.items.Enchantment;
import game.items.ItemStack;

import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import braynstorm.commonlib.Logger;
import braynstorm.commonlib.math.Vector3f;
import braynstorm.commonlib.network.PacketType;

public class PacketManager {
	public static void processPacket(short opcode, short len, ByteBuffer data){
		switch(opcode){
			case PacketType.LOGIN_STATUS:
				byte status = data.get();
				
				if(status == 1){
					// WooHoo
					byte numChars = data.get();
					short nameLen;
					short zoneNameLen;
					int raceData;
					StringBuilder sbName = new StringBuilder(30);
					StringBuilder sbZoneName = new StringBuilder(50);
					List<ShellCharacter> characters = new ArrayList<ShellCharacter>(numChars + 1);
					
					for(byte i = 0; i < numChars; i++){
						raceData = data.getInt();
						nameLen = data.getShort();
						zoneNameLen = data.getShort();
						
						short j;
						
						for(j = 0; j < nameLen; j++){
							sbName.append(data.getChar());
						}
						
						for(j = 0; j < zoneNameLen; j++){
							sbZoneName.append(data.getChar());
						}
						
						byte equipmentLen = data.get();
						
						Map<Character, ItemStack> equipment = new HashMap<>(equipmentLen + 1);
						
						for(j = 0; j < equipmentLen; j++){
							char slotID = data.getChar();
							ItemStack itemStack = new ItemStack(data.getInt(), data.getInt(), 1);
							itemStack.enchant(Enchantment.getEnchantment(data.getInt()));
							equipment.put(slotID, itemStack);
						}
						
						characters.add(new ShellCharacter(sbName.toString(), sbZoneName.toString(), raceData, equipment));
					}
					
					return;
				}
				
				if(status == 2){
					Date until = new Date(data.getLong());
					Logger.logInfo("Account is suspended until " + DateFormat.getInstance().format(until));
					return;
				}
				
				if(status == 3){
					Logger.logInfo("Account doesn't exist");
					return;
				}
				
				if(status == 4){
					Logger.logInfo("Too many login attempts");
					return;
				}
				
				break;
			case PacketType.ENTITY_MOTION_UPDATE:
				int id = data.getInt();
				Vector3f position = Vector3f.readFromBuffer(data);
				Vector3f forward = Vector3f.readFromBuffer(data);
				Vector3f up = Vector3f.readFromBuffer(data);
				boolean isInMotion = data.get() == 1 ? true : false;
				
				
				break;
		}
	}
}
