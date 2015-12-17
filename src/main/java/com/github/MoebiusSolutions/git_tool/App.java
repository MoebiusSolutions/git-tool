package com.github.MoebiusSolutions.git_tool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class App {
	
    public static void main( String[] args ) throws Exception
    {
    	LinkedList<String> argList = new LinkedList<String>(Arrays.asList(args));
    	if (argList.size() < 1) {
    		printUsageAndTerminate();
    	}
    	
    	// Process args
    	while (argList.size() > 0) {
    		String arg = argList.removeFirst();
    		
    		// "fix-urls"
    		if ("fix-urls".equals(arg)) {
    	    	boolean doSimulate = false;
    			// Process params to "fix-urls"
    			while (argList.size() > 1) {
    				String param = getRequiredArg(argList);
    				switch (param) {
    				case "--simulate":
    					doSimulate = true;
    					continue;
					default:
						printUsageAndTerminate();
    				}
    			}
    			String basePath = getRequiredArg(argList);
    			fixUrls(Paths.get(basePath), doSimulate);
    			continue;
    		}
    		
    		// Unrecognized argument
    		printUsageAndTerminate();
    	}
    }
    
    /**
	 * Terminates the program and prints usage if an arg is not available in the
	 * provided list. Removes the first arg and returns it if one is available.
	 */
    private static String getRequiredArg(LinkedList<String> argList) {
    	if (argList.size() < 1) {
    		printUsageAndTerminate();
    	}
    	return argList.removeFirst();
    }

    private static void fixUrls(Path baseDir, boolean doSimulate) {
    	if (doSimulate) {
	    	System.out.println("");
	    	System.out.println("*** Executing without Side Effects (--simulate) **");
	    	System.out.println("");
    	}
    	
    	// Find git dirs
    	List<Path> gitDirs = findGitDirs(baseDir);

    	GitRemoteParser processor =
    			new GitRemoteParser(
    					new GitRemoteTranslator(
    							new GitRemoteUpdater(System.out).setSimulate(doSimulate)));
    	for (Path repoDir : gitDirs) {
        	String tty = ProcessLauncher.I.launchAndWait(repoDir, "git", "remote", "-v");
        	processor.handle(new GitRemoteTty(repoDir, tty));
    	}
    }

    private static List<Path> findGitDirs(Path baseDir) {
    	try {
	    	GitDirFinder gitDirs = new GitDirFinder();
	    	Files.walkFileTree(baseDir, gitDirs);
	    	return gitDirs.getRepoDirs();
    	} catch (IOException e) {
    		throw new RuntimeException(e);
    	}
    }

	private static void printUsageAndTerminate() {
		System.out.printf("%n");
		System.out.printf("Usage: %n");
		System.out.printf("  java -jar git-tool.jar [fix-urls [--simulate] <base-directory>]%n", App.class.getName());
		System.out.printf("%n");
		System.out.printf("  fix-urls%n");
		System.out.printf("    Upates the remote urls of moesol repos from the old format to the new (Dec 2015).%n");
		System.out.printf("  --simulate%n");
		System.out.printf("    Simulates execution without any side-effects.%n");
		System.out.printf("  base-directory%n");
		System.out.printf("    The base directory to search in. This may contain any number of git repos nested at any depth.%n");
		System.out.printf("%n");
		System.exit(1);
	}
}
