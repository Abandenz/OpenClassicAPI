package ch.spacebase.openclassic.api.block;

import ch.spacebase.openclassic.api.Position;
import ch.spacebase.openclassic.api.entity.Entity;

public class Block {

	private Position pos;
	
	public Block(Position pos) {
		this.pos = pos;
	}
	
	public byte getTypeId() {
		return this.pos.getLevel().getBlockIdAt(pos);
	}
	
	public void setTypeId(byte id) {
		this.pos.getLevel().setBlockIdAt(pos, id);
	}
	
	public BlockType getType() {
		return BlockType.fromId(this.getTypeId());
	}
	
	public void setType(BlockType block) {
		this.setTypeId(block.getId());
	}
	
	public Position getPosition() {
		return this.pos;
	}
	
	public Block getRelative(BlockFace face) {
		Position pos = this.pos.clone().add(face.getModX(), face.getModY(), face.getModZ());
		if(pos.getBlockX() >= this.pos.getLevel().getWidth() || pos.getBlockX() < 0) return null;
		if(pos.getBlockY() >= this.pos.getLevel().getHeight() || pos.getBlockY() < 0) return null;
		if(pos.getBlockZ() >= this.pos.getLevel().getDepth() || pos.getBlockZ() < 0) return null;
		
		return new Block(pos);
	}
	
	public Entity getEntity() {
		return this.pos.getLevel().getEntity(this.pos);
	}
	
	public boolean isEntity() {
		return this.getEntity() != null;
	}
	
}