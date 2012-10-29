package ch.spacebase.openclassic.api.block.physics;

import ch.spacebase.openclassic.api.block.Block;
import ch.spacebase.openclassic.api.block.BlockFace;
import ch.spacebase.openclassic.api.block.VanillaBlock;
import ch.spacebase.openclassic.api.block.physics.BlockPhysics;

/**
 * Physics used for flowers.
 */
public class CactusPhysics implements BlockPhysics {

	@Override
	public void update(Block block) {
		Block b = block.getLevel().getBlockAt(block.getPosition().getBlockX(), block.getPosition().getBlockY() - 1, block.getPosition().getBlockZ());
		if (!block.getLevel().isLit(block.getPosition().getBlockX(), block.getPosition().getBlockY(), block.getPosition().getBlockZ()) || b.getType() != VanillaBlock.CACTUS && b.getType() != VanillaBlock.SAND) {
			block.setType(VanillaBlock.AIR);
		}
	}

	@Override
	public void onPlace(Block block) {
	}
	
	@Override
	public boolean canPlace(Block block) {
		Block b = block.getRelative(BlockFace.DOWN);
		return b.getType() == VanillaBlock.SAND || b.getType() == VanillaBlock.CACTUS;
	}

	@Override
	public void onBreak(Block block) {
	}

	@Override
	public void onNeighborChange(Block block, Block neighbor) {
		Block b = block.getRelative(BlockFace.DOWN);
		if(b.getType() != VanillaBlock.SAND && b.getType() != VanillaBlock.CACTUS) {
			block.setType(VanillaBlock.AIR);
		}
	}

}
