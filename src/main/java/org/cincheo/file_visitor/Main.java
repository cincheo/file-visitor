package org.cincheo.file_visitor;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

/**
 * Default entry point of the File Visitor. This program just visits and prints
 * the files as parameterized in the option file, but does not do anything else.
 * It must be specialized to perform actual actions.
 */
public class Main {

	/**
	 * To be set by a specialized visitor to tune the configuration file name.
	 */
	public static String confFileName = "file_visitor.json";
	/**
	 * To be set by a specialized visitor to tune the visitor (actually
	 * implements new behavior).
	 */
	public static FileVisitor<?> fileVisitor = new DefaultFileVisitor();

	@SuppressWarnings("unchecked")
	private static <O extends Options> FileVisitor<O> getFileVisitor() {
		return (FileVisitor<O>) fileVisitor;
	}

	/**
	 * This default file visitor uses the {@link DefaultFileVisitor} class to
	 * print out the visited files.
	 * 
	 * <p>
	 * This program should be specialized by creating a new main that will
	 * configure and delegate to this default main.
	 * 
	 * <pre>
	 * public static void main(String[] args) throws Exception {
	 * 	org.cincheo.file_visitor.Main.confFileName = "myconf.json";
	 * 	org.cincheo.file_visitor.Main.fileVisitor = new MyFileVisitor();
	 * 	org.cincheo.file_visitor.Main.main(args);
	 * }
	 * </pre>
	 * 
	 * @param args
	 *            args[0] contains the working directory
	 */

	public static <T extends Options> void main(String[] args) throws Exception {

		File workingDirectory = new File(".");

		if (!workingDirectory.exists()) {
			System.err.println("working directory does not exist: '" + workingDirectory.getAbsolutePath() + "'");
			System.exit(-1);
		}

		if (!workingDirectory.isDirectory()) {
			System.err.println("not a directory: '" + workingDirectory.getAbsolutePath() + "'");
			System.exit(-1);
		}

		File conf = new File(confFileName);
		if (!conf.exists()) {
			System.err.println("cannot find configuration file '" + conf + "' in working directory '"
					+ workingDirectory.getAbsolutePath() + "'");
			System.exit(-1);
		}

		String confJson = FileUtils.readFileToString(conf);
		Options options = new Gson().fromJson(confJson, fileVisitor.getOptionsClass());
		options.init();

		getFileVisitor().onBegin(options);
		File f = new File(options.in);
		if (f.exists()) {
			visitFile(options, f);
		}
		getFileVisitor().onEnd(options);
	}

	private static <T extends Options> void visitFile(T options, File f) {
		String path = f.getPath().equals(options.in) ? "" : f.getPath().substring(options.in.length() + 1);
		if (f.isFile()) {
			if (options.includePattern != null && !options.includePattern.matcher(path).matches()) {
				return;
			}
			if (options.excludePattern != null && options.excludePattern.matcher(path).matches()) {
				return;
			}
		}
		if (f.isDirectory()) {
			if (options.includeDirPattern != null && !options.includeDirPattern.matcher(path).matches()) {
				return;
			}
			if (options.excludeDirPattern != null && options.excludeDirPattern.matcher(path).matches()) {
				return;
			}
		}
		if (getFileVisitor().visit(options, f)) {
			if (f.isDirectory()) {
				for (File child : f.listFiles()) {
					if (child.isFile()) {
						visitFile(options, child);
					}
				}
				for (File child : f.listFiles()) {
					if (child.isDirectory()) {
						visitFile(options, child);
					}
				}
			}
		}
	}

}
