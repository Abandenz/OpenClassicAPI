package ch.spacebase.openclassic.api.item.physics;

import java.util.ArrayList;
import java.util.List;

import ch.spacebase.openclassic.api.block.Block;
import ch.spacebase.openclassic.api.block.BlockType;
import ch.spacebase.openclassic.api.inventory.ItemStack;
import ch.spacebase.openclassic.api.player.Player;

public class AxePhysics extends ItemPhysics {
	
	private static final List<BlockType> harvest = new ArrayList<BlockType>();
	
	public static void addBlock(BlockType block) {
		harvest.add(block);
	}
	
	public static List<BlockType> getBlocks() {
		return new ArrayList<BlockType>(harvest);
	}
	
	private float breakSpeed;
	
	public AxePhysics(float breakSpeed) {
		this.breakSpeed = breakSpeed;
	}
	
	@Override
	public boolean onRightClick(ItemStack item, Player player, Block block) {
		return false;
	}

	@Override
	public void onLeftClick(ItemStack item, Player player, Block block) {
	}
	
	@Override
	public float getBreakSpeed(BlockType block) {
		return harvest.contains(block) ? this.breakSpeed : 1;
	}

}
