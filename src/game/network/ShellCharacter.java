package game.network;

import game.items.ItemStack;

import java.nio.ByteBuffer;
import java.util.Map;

public class ShellCharacter {
	private String name;
	private String zoneName;
	private int raceData;
	private Map<Character, ItemStack> equipment;
	
	public ShellCharacter(String name, String zoneName, int raceData, Map<Character, ItemStack> equipment) {
		super();
		this.name = name;
		this.zoneName = zoneName;
		this.raceData = raceData;
		this.equipment = equipment;
	}
	
	public static ShellCharacter createFromPacket(ByteBuffer packet){
		int raceData = 0;
		Map<Character, ItemStack> equipment = null;
		
		StringBuilder sbName = new StringBuilder();
		StringBuilder sbZoneName = new StringBuilder();
		
		//packet.
		
		
		return new ShellCharacter(sbName.toString(), sbZoneName.toString(), raceData, equipment);
	}
}
