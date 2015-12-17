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
    	boolean doSimulate = false;

    	// Parse args
    	LinkedList<String> argList = new LinkedList<String>(Arrays.asList(args));
    	while (argList.size() > 1) {
    		String arg = argList.removeFirst();
    		if ("--simulate".equals(arg)) {
    			doSimulate = true;
    			continue;
    		}
    		// Unrecognized argument
    		printUsageAndTerminate();
    	}
    	if (argList.size() != 1) {
    		printUsageAndTerminate();
    	}
    	Path baseDir = Paths.get(argList.removeFirst());

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
    
    private static List<Path> findGitDirs(Path baseDir) throws IOException {
    	GitDirFinder gitDirs = new GitDirFinder();
    	Files.walkFileTree(baseDir, gitDirs);
    	return gitDirs.getRepoDirs();
    }

	private static void printUsageAndTerminate() {
		System.out.printf("%n");
		System.out.printf("Usage: %n");
		System.out.printf("  java git-tool.jar [--simulate] <base-directory>%n", App.class.getName());
		System.out.printf("%n");
		System.out.printf("  --simulate%n");
		System.out.printf("    Simulates execution without any side-effects.%n");
		System.out.printf("%n");
		System.exit(1);
	}
}
