package com.github.steveice10.openclassic.api.util;

import java.io.File;
import java.io.FilenameFilter;

public class JarFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {
		return name.endsWith(".jar");
	}

}
