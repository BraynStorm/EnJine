package game.items;

public class ItemStack {
	private Item item;
	private int metadata;
	private int amount;
	
	public ItemStack(Item item){
		this(item, 0, 1);
	}
	
	public ItemStack(int id, int metadata, int amount){
		this(Item.getByID(id), metadata, amount);
	}
	
	public ItemStack(Item item, int amount){
		this(item, 0, amount);
	}
	
	public ItemStack(Item item, int metadata, int amount){
		this.item = item;
		this.amount = amount;
	}

	public int getMetadata() {
		return metadata;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Item getItem() {
		return item;
	}
	
	
}
