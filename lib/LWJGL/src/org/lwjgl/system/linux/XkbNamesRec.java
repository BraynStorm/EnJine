/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 * MACHINE GENERATED FILE, DO NOT EDIT
 */
package org.lwjgl.system.linux;

import java.nio.*;

import org.lwjgl.*;
import static org.lwjgl.system.Checks.*;
import static org.lwjgl.system.MemoryUtil.*;

/** Describes an Xkb keyboard. */
public final class XkbNamesRec implements Pointer {

	/** The struct size in bytes. */
	public static final int SIZEOF;

	/** The struct member offsets. */
	public static final int
		KEYCODES,
		GEOMETRY,
		SYMBOLS,
		TYPES,
		COMPAT,
		VMODS,
		INDICATORS,
		GROUPS,
		KEYS,
		KEY_ALIASES,
		RADIO_GROUPS,
		PHYS_SYMBOLS,
		NUM_KEYS,
		NUM_KEY_ALIASES,
		NUM_RG;

	static {
		IntBuffer offsets = BufferUtils.createIntBuffer(15);

		SIZEOF = offsets(memAddress(offsets));

		KEYCODES = offsets.get(0);
		GEOMETRY = offsets.get(1);
		SYMBOLS = offsets.get(2);
		TYPES = offsets.get(3);
		COMPAT = offsets.get(4);
		VMODS = offsets.get(5);
		INDICATORS = offsets.get(6);
		GROUPS = offsets.get(7);
		KEYS = offsets.get(8);
		KEY_ALIASES = offsets.get(9);
		RADIO_GROUPS = offsets.get(10);
		PHYS_SYMBOLS = offsets.get(11);
		NUM_KEYS = offsets.get(12);
		NUM_KEY_ALIASES = offsets.get(13);
		NUM_RG = offsets.get(14);
	}

	private final ByteBuffer struct;

	public XkbNamesRec() {
		this(malloc());
	}

	public XkbNamesRec(ByteBuffer struct) {
		if ( LWJGLUtil.CHECKS )
			checkBuffer(struct, SIZEOF);

		this.struct = struct;
	}

	public ByteBuffer buffer() {
		return struct;
	}

	@Override
	public long getPointer() {
		return memAddress(struct);
	}

	public void setKeycodes(long keycodes) { keycodes(struct, keycodes); }
	public void setGeometry(long geometry) { geometry(struct, geometry); }
	public void setSymbols(long symbols) { symbols(struct, symbols); }
	public void setTypes(long types) { types(struct, types); }
	public void setCompat(long compat) { compat(struct, compat); }
	public void setVmods(long vmods, int bytes) { vmodsSet(struct, vmods, bytes); }
	public void setVmods(ByteBuffer vmods) { vmodsSet(struct, vmods); }
	public void setVmods(int index, long element) { vmodsSet(struct, index, element); }
	public void setIndicators(long indicators, int bytes) { indicatorsSet(struct, indicators, bytes); }
	public void setIndicators(ByteBuffer indicators) { indicatorsSet(struct, indicators); }
	public void setIndicators(int index, long element) { indicatorsSet(struct, index, element); }
	public void setGroups(long groups, int bytes) { groupsSet(struct, groups, bytes); }
	public void setGroups(ByteBuffer groups) { groupsSet(struct, groups); }
	public void setGroups(int index, long element) { groupsSet(struct, index, element); }
	public void setKeys(long keys) { keys(struct, keys); }
	public void setKeys(ByteBuffer keys) { keys(struct, keys); }
	public void setKeyAliases(long key_aliases) { key_aliases(struct, key_aliases); }
	public void setKeyAliases(ByteBuffer key_aliases) { key_aliases(struct, key_aliases); }
	public void setRadioGroups(long radio_groups) { radio_groups(struct, radio_groups); }
	public void setRadioGroups(ByteBuffer radio_groups) { radio_groups(struct, radio_groups); }
	public void setPhysSymbols(long phys_symbols) { phys_symbols(struct, phys_symbols); }
	public void setNumKeys(int num_keys) { num_keys(struct, num_keys); }
	public void setNumKeyAliases(int num_key_aliases) { num_key_aliases(struct, num_key_aliases); }
	public void setNumRg(int num_rg) { num_rg(struct, num_rg); }

	public long getKeycodes() { return keycodes(struct); }
	public long getGeometry() { return geometry(struct); }
	public long getSymbols() { return symbols(struct); }
	public long getTypes() { return types(struct); }
	public long getCompat() { return compat(struct); }
	public void getVmods(long vmods, int bytes) { vmodsGet(struct, vmods, bytes); }
	public void getVmods(ByteBuffer vmods) { vmodsGet(struct, vmods); }
	public void getIndicators(long indicators, int bytes) { indicatorsGet(struct, indicators, bytes); }
	public void getIndicators(ByteBuffer indicators) { indicatorsGet(struct, indicators); }
	public void getGroups(long groups, int bytes) { groupsGet(struct, groups, bytes); }
	public void getGroups(ByteBuffer groups) { groupsGet(struct, groups); }
	public long getKeys() { return keys(struct); }
	public ByteBuffer getKeysBuffer() { return keysBuffer(struct); }
	public long getKeyAliases() { return key_aliases(struct); }
	public ByteBuffer getKeyAliasesBuffer() { return key_aliasesBuffer(struct); }
	public long getRadioGroups() { return radio_groups(struct); }
	public ByteBuffer getRadioGroups(int size) { return radio_groups(struct, size); }
	public long getPhysSymbols() { return phys_symbols(struct); }
	public int getNumKeys() { return num_keys(struct); }
	public int getNumKeyAliases() { return num_key_aliases(struct); }
	public int getNumRg() { return num_rg(struct); }

	// -----------------------------------

	private static native int offsets(long buffer);

	/** Returns a new {@link ByteBuffer} instance with a capacity equal to {@link #SIZEOF}. */
	public static ByteBuffer malloc() { return BufferUtils.createByteBuffer(SIZEOF); }

	/** Virtual constructor. Calls {@link #malloc} and initializes the returned {@link ByteBuffer} instance with the specified values. */
	public static ByteBuffer malloc(
		long keycodes,
		long geometry,
		long symbols,
		long types,
		long compat,
		long vmods,
		int vmodsBytes,
		long indicators,
		int indicatorsBytes,
		long groups,
		int groupsBytes,
		long keys,
		long key_aliases,
		long radio_groups,
		long phys_symbols,
		int num_keys,
		int num_key_aliases,
		int num_rg
	) {
		ByteBuffer xkbnamesrec = malloc();

		keycodes(xkbnamesrec, keycodes);
		geometry(xkbnamesrec, geometry);
		symbols(xkbnamesrec, symbols);
		types(xkbnamesrec, types);
		compat(xkbnamesrec, compat);
		vmodsSet(xkbnamesrec, vmods, vmodsBytes);
		indicatorsSet(xkbnamesrec, indicators, indicatorsBytes);
		groupsSet(xkbnamesrec, groups, groupsBytes);
		keys(xkbnamesrec, keys);
		key_aliases(xkbnamesrec, key_aliases);
		radio_groups(xkbnamesrec, radio_groups);
		phys_symbols(xkbnamesrec, phys_symbols);
		num_keys(xkbnamesrec, num_keys);
		num_key_aliases(xkbnamesrec, num_key_aliases);
		num_rg(xkbnamesrec, num_rg);

		return xkbnamesrec;
	}

	/** Alternative virtual constructor. */
	public static ByteBuffer malloc(
		long keycodes,
		long geometry,
		long symbols,
		long types,
		long compat,
		ByteBuffer vmods,
		ByteBuffer indicators,
		ByteBuffer groups,
		ByteBuffer keys,
		ByteBuffer key_aliases,
		ByteBuffer radio_groups,
		long phys_symbols,
		int num_keys,
		int num_key_aliases,
		int num_rg
	) {
		ByteBuffer xkbnamesrec = malloc();

		keycodes(xkbnamesrec, keycodes);
		geometry(xkbnamesrec, geometry);
		symbols(xkbnamesrec, symbols);
		types(xkbnamesrec, types);
		compat(xkbnamesrec, compat);
		vmodsSet(xkbnamesrec, vmods);
		indicatorsSet(xkbnamesrec, indicators);
		groupsSet(xkbnamesrec, groups);
		keys(xkbnamesrec, keys);
		key_aliases(xkbnamesrec, key_aliases);
		radio_groups(xkbnamesrec, radio_groups);
		phys_symbols(xkbnamesrec, phys_symbols);
		num_keys(xkbnamesrec, num_keys);
		num_key_aliases(xkbnamesrec, num_key_aliases);
		num_rg(xkbnamesrec, num_rg);

		return xkbnamesrec;
	}

	public static void keycodes(ByteBuffer xkbnamesrec, long keycodes) { PointerBuffer.put(xkbnamesrec, xkbnamesrec.position() + KEYCODES, keycodes); }
	public static void geometry(ByteBuffer xkbnamesrec, long geometry) { PointerBuffer.put(xkbnamesrec, xkbnamesrec.position() + GEOMETRY, geometry); }
	public static void symbols(ByteBuffer xkbnamesrec, long symbols) { PointerBuffer.put(xkbnamesrec, xkbnamesrec.position() + SYMBOLS, symbols); }
	public static void types(ByteBuffer xkbnamesrec, long types) { PointerBuffer.put(xkbnamesrec, xkbnamesrec.position() + TYPES, types); }
	public static void compat(ByteBuffer xkbnamesrec, long compat) { PointerBuffer.put(xkbnamesrec, xkbnamesrec.position() + COMPAT, compat); }
	public static void vmodsSet(ByteBuffer xkbnamesrec, long vmods, int bytes) { memCopy(vmods, memAddress(xkbnamesrec) + VMODS, bytes); }
	public static void vmodsSet(ByteBuffer xkbnamesrec, ByteBuffer vmods) {
		checkBufferGT(vmods, 16 * POINTER_SIZE);
		vmodsSet(xkbnamesrec, memAddress(vmods), vmods.remaining());
	}
	public static void vmodsSet(ByteBuffer xkbnamesrec, int index, long element) { PointerBuffer.put(xkbnamesrec, VMODS + index * POINTER_SIZE, element); }
	public static void indicatorsSet(ByteBuffer xkbnamesrec, long indicators, int bytes) { memCopy(indicators, memAddress(xkbnamesrec) + INDICATORS, bytes); }
	public static void indicatorsSet(ByteBuffer xkbnamesrec, ByteBuffer indicators) {
		checkBufferGT(indicators, 32 * POINTER_SIZE);
		indicatorsSet(xkbnamesrec, memAddress(indicators), indicators.remaining());
	}
	public static void indicatorsSet(ByteBuffer xkbnamesrec, int index, long element) { PointerBuffer.put(xkbnamesrec, INDICATORS + index * POINTER_SIZE, element); }
	public static void groupsSet(ByteBuffer xkbnamesrec, long groups, int bytes) { memCopy(groups, memAddress(xkbnamesrec) + GROUPS, bytes); }
	public static void groupsSet(ByteBuffer xkbnamesrec, ByteBuffer groups) {
		checkBufferGT(groups, 4 * POINTER_SIZE);
		groupsSet(xkbnamesrec, memAddress(groups), groups.remaining());
	}
	public static void groupsSet(ByteBuffer xkbnamesrec, int index, long element) { PointerBuffer.put(xkbnamesrec, GROUPS + index * POINTER_SIZE, element); }
	public static void keys(ByteBuffer xkbnamesrec, long keys) { PointerBuffer.put(xkbnamesrec, xkbnamesrec.position() + KEYS, keys); }
	public static void keys(ByteBuffer xkbnamesrec, ByteBuffer keys) { keys(xkbnamesrec, memAddress(keys)); }
	public static void key_aliases(ByteBuffer xkbnamesrec, long key_aliases) { PointerBuffer.put(xkbnamesrec, xkbnamesrec.position() + KEY_ALIASES, key_aliases); }
	public static void key_aliases(ByteBuffer xkbnamesrec, ByteBuffer key_aliases) { key_aliases(xkbnamesrec, memAddress(key_aliases)); }
	public static void radio_groups(ByteBuffer xkbnamesrec, long radio_groups) { PointerBuffer.put(xkbnamesrec, xkbnamesrec.position() + RADIO_GROUPS, radio_groups); }
	public static void radio_groups(ByteBuffer xkbnamesrec, ByteBuffer radio_groups) { radio_groups(xkbnamesrec, memAddress(radio_groups)); }
	public static void phys_symbols(ByteBuffer xkbnamesrec, long phys_symbols) { PointerBuffer.put(xkbnamesrec, xkbnamesrec.position() + PHYS_SYMBOLS, phys_symbols); }
	public static void num_keys(ByteBuffer xkbnamesrec, int num_keys) { xkbnamesrec.put(xkbnamesrec.position() + NUM_KEYS, (byte)num_keys); }
	public static void num_key_aliases(ByteBuffer xkbnamesrec, int num_key_aliases) { xkbnamesrec.put(xkbnamesrec.position() + NUM_KEY_ALIASES, (byte)num_key_aliases); }
	public static void num_rg(ByteBuffer xkbnamesrec, int num_rg) { xkbnamesrec.putShort(xkbnamesrec.position() + NUM_RG, (short)num_rg); }

	public static long keycodes(ByteBuffer xkbnamesrec) { return PointerBuffer.get(xkbnamesrec, xkbnamesrec.position() + KEYCODES); }
	public static long geometry(ByteBuffer xkbnamesrec) { return PointerBuffer.get(xkbnamesrec, xkbnamesrec.position() + GEOMETRY); }
	public static long symbols(ByteBuffer xkbnamesrec) { return PointerBuffer.get(xkbnamesrec, xkbnamesrec.position() + SYMBOLS); }
	public static long types(ByteBuffer xkbnamesrec) { return PointerBuffer.get(xkbnamesrec, xkbnamesrec.position() + TYPES); }
	public static long compat(ByteBuffer xkbnamesrec) { return PointerBuffer.get(xkbnamesrec, xkbnamesrec.position() + COMPAT); }
	public static void vmodsGet(ByteBuffer xkbnamesrec, long vmods, int bytes) {
		memCopy(memAddress(xkbnamesrec) + VMODS, vmods, bytes);
	}
	public static void vmodsGet(ByteBuffer xkbnamesrec, ByteBuffer vmods) {
		checkBufferGT(vmods, 16 * 8);
		vmodsGet(xkbnamesrec, memAddress(vmods), vmods.remaining());
	}
	public static void indicatorsGet(ByteBuffer xkbnamesrec, long indicators, int bytes) {
		memCopy(memAddress(xkbnamesrec) + INDICATORS, indicators, bytes);
	}
	public static void indicatorsGet(ByteBuffer xkbnamesrec, ByteBuffer indicators) {
		checkBufferGT(indicators, 32 * 8);
		indicatorsGet(xkbnamesrec, memAddress(indicators), indicators.remaining());
	}
	public static void groupsGet(ByteBuffer xkbnamesrec, long groups, int bytes) {
		memCopy(memAddress(xkbnamesrec) + GROUPS, groups, bytes);
	}
	public static void groupsGet(ByteBuffer xkbnamesrec, ByteBuffer groups) {
		checkBufferGT(groups, 4 * 8);
		groupsGet(xkbnamesrec, memAddress(groups), groups.remaining());
	}
	public static long keys(ByteBuffer xkbnamesrec) { return PointerBuffer.get(xkbnamesrec, xkbnamesrec.position() + KEYS); }
	public static ByteBuffer keysBuffer(ByteBuffer xkbnamesrec) { return memByteBuffer(keys(xkbnamesrec), XkbKeyNameRec.SIZEOF); }
	public static long key_aliases(ByteBuffer xkbnamesrec) { return PointerBuffer.get(xkbnamesrec, xkbnamesrec.position() + KEY_ALIASES); }
	public static ByteBuffer key_aliasesBuffer(ByteBuffer xkbnamesrec) { return memByteBuffer(key_aliases(xkbnamesrec), XkbKeyAliasRec.SIZEOF); }
	public static long radio_groups(ByteBuffer xkbnamesrec) { return PointerBuffer.get(xkbnamesrec, xkbnamesrec.position() + RADIO_GROUPS); }
	public static ByteBuffer radio_groups(ByteBuffer xkbnamesrec, int size) { long address = radio_groups(xkbnamesrec); return address == NULL ? null : memByteBuffer(address, size); }
	public static long phys_symbols(ByteBuffer xkbnamesrec) { return PointerBuffer.get(xkbnamesrec, xkbnamesrec.position() + PHYS_SYMBOLS); }
	public static int num_keys(ByteBuffer xkbnamesrec) { return xkbnamesrec.get(xkbnamesrec.position() + NUM_KEYS) & 0xFF; }
	public static int num_key_aliases(ByteBuffer xkbnamesrec) { return xkbnamesrec.get(xkbnamesrec.position() + NUM_KEY_ALIASES) & 0xFF; }
	public static int num_rg(ByteBuffer xkbnamesrec) { return xkbnamesrec.getShort(xkbnamesrec.position() + NUM_RG) & 0xFFFF; }

}