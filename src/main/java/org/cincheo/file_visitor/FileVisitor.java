package org.cincheo.file_visitor;

import java.io.File;

/**
 * An interface to be implemented to create actual actions on filed being
 * visited.
 */
public interface FileVisitor<O extends Options> {

	/**
	 * Must return the options class.
	 */
	public Class<O> getOptionsClass();

	/**
	 * This method is called right before the visiting process begins.
	 */
	public void onBegin(O options);

	/**
	 * This method is called each time a file is visited.
	 * 
	 * @param options
	 *            the options for the visit process
	 * @param the
	 *            file being visited
	 * @return should return true in order to continue, or false to end visiting
	 *         (on a directory, returning false will skip all the remaining
	 *         files in the directory)
	 */
	public boolean visit(O options, File file);

	/**
	 * This method is called right after the visiting process ends.
	 */
	public void onEnd(O options);
}
