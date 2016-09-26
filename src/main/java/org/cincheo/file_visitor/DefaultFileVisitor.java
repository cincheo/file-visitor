package org.cincheo.file_visitor;

import java.io.File;

public class DefaultFileVisitor implements FileVisitor<Options> {

	int count = 0;

	@Override
	public Class<Options> getOptionsClass() {
		return Options.class;
	}

	@Override
	public void onBegin(Options options) {
		System.out.println("starting visiting: " + options);
	}

	@Override
	public boolean visit(Options options, File file) {
		System.out.println("visiting: " + file);
		count++;
		return true;
	}

	@Override
	public void onEnd(Options options) {
		System.out.println("ended visiting: " + count + " file(s) visited");
	}

}
