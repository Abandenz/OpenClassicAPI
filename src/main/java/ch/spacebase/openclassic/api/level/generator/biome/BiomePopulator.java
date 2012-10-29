package ch.spacebase.openclassic.api.level.generator.biome;

import java.util.Random;

import ch.spacebase.openclassic.api.level.Level;
import ch.spacebase.openclassic.api.level.column.Chunk;
import ch.spacebase.openclassic.api.level.generator.Populator;

public class BiomePopulator implements Populator {

	@Override
	public void populate(Level level, Chunk chunk, Random random) {
		Biome biome = chunk.getBiome(chunk.getWorldX() + 7, chunk.getWorldY() + 7, chunk.getWorldZ() + 7);
		if(biome != null) {
			biome.decorate(level, chunk, random);
		}
	}

}
