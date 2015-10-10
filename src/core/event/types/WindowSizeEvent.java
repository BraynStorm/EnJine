package core.event.types;

public class WindowSizeEvent extends Event{
	public int width;
	public int height;
	
	public WindowSizeEvent(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
}
