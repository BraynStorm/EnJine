package game.items;

import java.util.HashMap;
import java.util.Map;

import enjine.core.gl.Texture;

public class Item {
	public static final Map<Integer, Item> itemList = new HashMap<>();
	
	int id;
	int maxStack;
	
	Texture icon;

	public static Item getByID(int id) {
		return itemList.get(id);
	}
}
