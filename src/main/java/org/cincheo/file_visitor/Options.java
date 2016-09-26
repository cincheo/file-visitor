package org.cincheo.file_visitor;

import java.util.regex.Pattern;

/**
 * This class should be sub-classed by specialized programs in order to accept
 * other visiting options.
 */
public class Options {

	public String in;

	public String includeFilter;
	public String includeDirFilter;
	public String excludeFilter;
	public String excludeDirFilter;

	public Pattern includePattern;
	public Pattern includeDirPattern;
	public Pattern excludePattern;
	public Pattern excludeDirPattern;

	/**
	 * This method shall be overloaded to check and initialize the options. When
	 * an critical error is found in the options, use
	 * <code>System.exit(-1)</code> to exit.
	 */
	public void init() {
		if (in == null) {
			System.err.println("missing mandatory parameter 'in'");
			System.exit(-1);
		}
		includePattern = includeFilter == null ? null
				: Pattern.compile(includeFilter.replace(".", "\\.").replace("*", ".*"));
		includeDirPattern = includeDirFilter == null ? null
				: Pattern.compile(includeDirFilter.replace(".", "\\.").replace("*", ".*"));
		excludePattern = excludeFilter == null ? null
				: Pattern.compile(excludeFilter.replace(".", "\\.").replace("*", ".*"));
		excludeDirPattern = excludeDirFilter == null ? null
				: Pattern.compile(excludeDirFilter.replace(".", "\\.").replace("*", ".*"));
	}

	@Override
	public String toString() {
		return "{ in=" + in + ", includeFilter=" + includeFilter + ", includeDirFilter=" + includeDirFilter
				+ ", excludeFilter=" + excludeFilter + ", excludeDirFilter=" + excludeDirFilter + " }";
	}

}
