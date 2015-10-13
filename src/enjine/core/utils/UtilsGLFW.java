package enjine.core.utils;

public class UtilsGLFW {
	public static boolean isShiftDown(int modifiers){ return (modifiers & 1) == 1; }
	public static boolean isCtrlDown(int modifiers)	{ return (modifiers & 2) == 1; }
	public static boolean isAltDown(int modifiers)	{ return (modifiers & 4) == 1; }
	public static boolean isSuperDown(int modifiers){ return (modifiers & 8) == 1; }
}
