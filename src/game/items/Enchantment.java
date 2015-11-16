package game.items;

import java.util.HashMap;
import java.util.Map;

public class Enchantment {
	public static final Map<Integer, Enchantment> enchantmentsList = new HashMap<>();
	public static Enchantment getEnchantment(int id) {// XXX probably should throw an exception if null;
		return enchantmentsList.get(id);
	}
	
	private int id;
	private int effectID;
	
	public Enchantment(int id, int effectID){
		this.id = id;
		this.effectID = effectID;
	}
	
	public int getId() {
		return id;
	}
	
	public int getEffectID() {
		return effectID;
	}
	
}
