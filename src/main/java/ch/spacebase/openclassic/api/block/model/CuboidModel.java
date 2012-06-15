package ch.spacebase.openclassic.api.block.model;

import ch.spacebase.openclassic.api.OpenClassic;
import ch.spacebase.openclassic.api.block.Block;
import ch.spacebase.openclassic.api.block.BlockFace;
import ch.spacebase.openclassic.api.render.RenderHelper;

public class CuboidModel extends Model {
	
	public CuboidModel(Texture texture, int[] textureIds, float x1, float y1, float z1, float x2, float y2, float z2) {
		if(textureIds.length < 6) {
			throw new IllegalArgumentException("Texture ID array must have length of 6!");
		}
		
		this.setCollisionBox(x1, y1, z1, x2, y2, z2);
		this.setSelectionBox(x1, y1, z1, x2, y2, z2);
		
		Quad bottom = new Quad(0, texture.getSubTexture(textureIds[0]));
		bottom.addVertex(0, x1, y1, z1);
		bottom.addVertex(1, x2, y1, z1);
		bottom.addVertex(2, x2, y1, z2);
		bottom.addVertex(3, x1, y1, z2);
		this.addQuad(bottom);
		
		Quad top = new Quad(1, texture.getSubTexture(textureIds[1]));
		top.addVertex(0, x1, y2, z1);
		top.addVertex(1, x1, y2, z2);
		top.addVertex(2, x2, y2, z2);
		top.addVertex(3, x2, y2, z1);
		this.addQuad(top);

		Quad face1 = new Quad(2, texture.getSubTexture(textureIds[2]));
		face1.addVertex(0, x1, y1, z1);
		face1.addVertex(1, x1, y2, z1);
		face1.addVertex(2, x2, y2, z1);
		face1.addVertex(3, x2, y1, z1);
		this.addQuad(face1);

		Quad face2 = new Quad(3, texture.getSubTexture(textureIds[3]));
		face2.addVertex(0, x2, y1, z2);
		face2.addVertex(1, x2, y2, z2);
		face2.addVertex(2, x1, y2, z2);
		face2.addVertex(3, x1, y1, z2);
		this.addQuad(face2);

		Quad face3 = new Quad(4, texture.getSubTexture(textureIds[4]));
		face3.addVertex(0, x1, y1, z2);
		face3.addVertex(1, x1, y2, z2);
		face3.addVertex(2, x1, y2, z1);
		face3.addVertex(3, x1, y1, z1);
		this.addQuad(face3);
		
		Quad face4 = new Quad(5, texture.getSubTexture(textureIds[5]));
		face4.addVertex(0, x2, y1, z1);
		face4.addVertex(1, x2, y2, z1);
		face4.addVertex(2, x2, y2, z2);
		face4.addVertex(3, x2, y1, z2);
		this.addQuad(face4);
	}
	
	public CuboidModel(Texture texture, int textureId, float x1, float y1, float z1, float x2, float y2, float z2) {
		this(texture, new int[] { textureId, textureId, textureId, textureId, textureId, textureId }, x1, y1, z1, x2, y2, z2);
	}
	
	public CuboidModel(String texture, int textureSize, float x1, float y1, float z1, float x2, float y2, float z2) {
		this(new Texture(texture, false, textureSize, textureSize, textureSize), 0, x1, y1, z1, x2, y2, z2);
	}
	
	@Override
	public boolean render(int x, int y, int z, float brightness) {
		Block block = OpenClassic.getClient().getLevel().getBlockAt(x, y, z);
		if(block == null) return false;
		boolean result = false;
		
		int count = 0;
		for(Quad quad : this.getQuads()) {
			BlockFace face = quadToFace(this, count);
			if (RenderHelper.getHelper().canRenderSide(block, face)) {
				float mod = 0;
				switch(face) {
					case DOWN:
						mod = 0.5F;
						break;
					case UP:
						mod = 1;
						break;
					case WEST:
					case EAST:
						mod = 0.8F;
						break;
					case SOUTH:
					case NORTH:
						mod = 0.6F;
						break;
				}
				
				quad.render(x, y, z, RenderHelper.getHelper().getBrightness(block.getType(), x + face.getModX(), y + face.getModY(), z + face.getModZ()) * mod);
				result = true;
			}
			
			count++;
		}
		
		return result;
	}
	
	public boolean renderAll(int x, int y, int z, float brightness) {
		return super.render(x, y, z, brightness);
	}
	
	public String getType() {
		return "CuboidModel";
	}
	
	public void renderFullbright(int x, int y, int z) {
		this.getQuad(0).render(x, y, z, 0.5F);
		this.getQuad(1).render(x, y, z, 1);
		this.getQuad(2).render(x, y, z, 0.8F);
		this.getQuad(3).render(x, y, z, 0.8F);
		this.getQuad(4).render(x, y, z, 0.6F);
		this.getQuad(5).render(x, y, z, 0.6F);
	}
	
	public static BlockFace quadToFace(CuboidModel model, int quad) {
		if(model instanceof LiquidModel) {
			switch(quad) {
			case 0: case 1: return BlockFace.DOWN;
			case 2: case 3: return BlockFace.UP;
			case 4: case 5: return BlockFace.WEST;
			case 6: case 7: return BlockFace.EAST;
			case 8: case 9: return BlockFace.SOUTH;
			case 10: case 11: return BlockFace.NORTH;
			default: return null;
		}
		} else {
			switch(quad) {
				case 0: return BlockFace.DOWN;
				case 1: return BlockFace.UP;
				case 2: return BlockFace.WEST;
				case 3: return BlockFace.EAST;
				case 4: return BlockFace.SOUTH;
				case 5: return BlockFace.NORTH;
				default: return null;
			}
		}
	}
	
	public static int faceToQuad(BlockFace face) {
		switch(face) {
			case DOWN: return 0;
			case UP: return 1;
			case WEST: return 2;
			case EAST: return 3;
			case SOUTH: return 4;
			case NORTH: return 5;
			default: return -1;
		}
	}
	
}