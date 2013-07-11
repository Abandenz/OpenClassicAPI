package ch.spacebase.openclassic.api.block.model;

import ch.spacebase.openclassic.api.OpenClassic;
import ch.spacebase.openclassic.api.asset.AssetSource;
import ch.spacebase.openclassic.api.asset.texture.Texture;
import ch.spacebase.openclassic.api.block.BlockFace;
import ch.spacebase.openclassic.api.block.BlockType;
import ch.spacebase.openclassic.api.render.RenderHelper;
import ch.spacebase.openclassic.api.util.Constants;

/**
 * A cuboid-shaped model.
 */
public class CuboidModel extends Model {
	
	private boolean cube = false;
	
	public CuboidModel(Texture texture, int[] textureIds, float x1, float y1, float z1, float x2, float y2, float z2) {
		if(textureIds.length < 6) {
			throw new IllegalArgumentException("Texture ID array must have length of 6!");
		}
		
		this.setCollisionBox(x1, y1, z1, x2, y2, z2);
		this.setSelectionBox(x1, y1, z1, x2, y2, z2);
		
		Quad bottom = new Quad(0, texture.getSubTexture(textureIds[0], Constants.TERRAIN_SIZE, Constants.TERRAIN_SIZE));
		bottom.addVertex(0, x1, y1, z1);
		bottom.addVertex(1, x2, y1, z1);
		bottom.addVertex(2, x2, y1, z2);
		bottom.addVertex(3, x1, y1, z2);
		this.addQuad(bottom);
		
		Quad top = new Quad(1, texture.getSubTexture(textureIds[1], Constants.TERRAIN_SIZE, Constants.TERRAIN_SIZE));
		top.addVertex(0, x1, y2, z1);
		top.addVertex(1, x1, y2, z2);
		top.addVertex(2, x2, y2, z2);
		top.addVertex(3, x2, y2, z1);
		this.addQuad(top);

		Quad face1 = new Quad(2, texture.getSubTexture(textureIds[2], Constants.TERRAIN_SIZE, Constants.TERRAIN_SIZE));
		face1.addVertex(0, x1, y1, z1);
		face1.addVertex(1, x1, y2, z1);
		face1.addVertex(2, x2, y2, z1);
		face1.addVertex(3, x2, y1, z1);
		this.addQuad(face1);

		Quad face2 = new Quad(3, texture.getSubTexture(textureIds[3], Constants.TERRAIN_SIZE, Constants.TERRAIN_SIZE));
		face2.addVertex(0, x2, y1, z2);
		face2.addVertex(1, x2, y2, z2);
		face2.addVertex(2, x1, y2, z2);
		face2.addVertex(3, x1, y1, z2);
		this.addQuad(face2);

		Quad face3 = new Quad(4, texture.getSubTexture(textureIds[4], Constants.TERRAIN_SIZE, Constants.TERRAIN_SIZE));
		face3.addVertex(0, x1, y1, z2);
		face3.addVertex(1, x1, y2, z2);
		face3.addVertex(2, x1, y2, z1);
		face3.addVertex(3, x1, y1, z1);
		this.addQuad(face3);
		
		Quad face4 = new Quad(5, texture.getSubTexture(textureIds[5], Constants.TERRAIN_SIZE, Constants.TERRAIN_SIZE));
		face4.addVertex(0, x2, y1, z1);
		face4.addVertex(1, x2, y2, z1);
		face4.addVertex(2, x2, y2, z2);
		face4.addVertex(3, x2, y1, z2);
		this.addQuad(face4);
		
		this.cube = x1 == 0 && y1 == 0 && z1 == 0 && x2 == 1 && y2 == 1 && z2 == 1;
	}
	
	public CuboidModel(Texture texture, int textureId, float x1, float y1, float z1, float x2, float y2, float z2) {
		this(texture, new int[] { textureId, textureId, textureId, textureId, textureId, textureId }, x1, y1, z1, x2, y2, z2);
	}
	
	public CuboidModel(String texture, AssetSource source, float x1, float y1, float z1, float x2, float y2, float z2) {
		this(OpenClassic.getGame().getAssetManager().load(texture, source, Texture.class), 0, x1, y1, z1, x2, y2, z2);
	}
	
	@Override
	public boolean render(BlockType block, float x, float y, float z, float brightness) {
		if(block == null) return false;
		boolean result = false;
		
		for(Quad quad : this.getQuads()) {
			BlockFace face = quadToFace(this, quad.getId());
			if (RenderHelper.getHelper().canRenderSide(block, (int) x, (int) y, (int) z, face)) {
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
				
				quad.render(x, y, z, RenderHelper.getHelper().getBrightness(block, (int) x + face.getModX(), (int) y + face.getModY(), (int) z + face.getModZ()) * mod);
				result = true;
			}
		}
		
		return result;
	}
	
	@Override
	public void renderAll(BlockType type, float x, float y, float z, float brightness) {
		this.getQuad(0).render(x, y, z, brightness * 0.5F);
		this.getQuad(1).render(x, y, z, brightness * 1);
		this.getQuad(2).render(x, y, z, brightness * 0.8F);
		this.getQuad(3).render(x, y, z, brightness * 0.8F);
		this.getQuad(4).render(x, y, z, brightness * 0.6F);
		this.getQuad(5).render(x, y, z, brightness * 0.6F);
	}
	
	@Override
	public void renderScaled(float x, float y, float z, float scale, float brightness) {
		this.getQuad(0).renderScaled(x, y, z, scale, brightness * 0.5F);
		this.getQuad(1).renderScaled(x, y, z, scale, brightness * 1);
		this.getQuad(2).renderScaled(x, y, z, scale, brightness * 0.8F);
		this.getQuad(3).renderScaled(x, y, z, scale, brightness * 0.8F);
		this.getQuad(4).renderScaled(x, y, z, scale, brightness * 0.6F);
		this.getQuad(5).renderScaled(x, y, z, scale, brightness * 0.6F);
	}
	
	@Override
	public boolean isCube() {
		return this.cube;
	}
	
	@Override
	public String getType() {
		return "CuboidModel";
	}
	
	/**
	 * Converts a quad ID to a BlockFace.
	 * @param model Model the quad belongs to.
	 * @param quad Quad ID to convert.
	 * @return The BlockFace the quad ID converts to.
	 */
	public static BlockFace quadToFace(CuboidModel model, int quad) {
		switch(quad) {
			case 0: return BlockFace.DOWN;
			case 1: return BlockFace.UP;
			case 2: return BlockFace.EAST;
			case 3: return BlockFace.WEST;
			case 4: return BlockFace.NORTH;
			case 5: return BlockFace.SOUTH;
			default: return null;
		}
	}
	
}
