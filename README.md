# file-visitor

This utility written in Java allows for visiting a set of files within a directory. It can be specialized to perform any job on the visited files.

The main program takes a unique argument, which is the working directory. The working directory should contain a configuration file written in JSON, that defines options.

Default options (which can be extended) are for visiting purpose.

- ``in``: the root directory to be visited (relative to the working directory)
-	``includeFilter`` [optional]: the filter to be used to include files (example: ``*.java``)
-	``includeDirFilter`` [optional]: the filter to be used to include directories
-	``excludeFilter`` [optional]: the filter to be used to exclude files (example: ``*.js``)
-	``excludeDirFilter`` [optional]: the filter to be used to exclude directories

## How to use

This file visitor does not do anything by itself. You can either modify it under the terms of the licence to adapt it to your own needs, or you can use it as is and extend the appropriate classes to create a new utility on the top of it.

## Licence

Apache 2.
