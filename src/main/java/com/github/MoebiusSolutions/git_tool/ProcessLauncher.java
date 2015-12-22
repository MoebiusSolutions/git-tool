package com.github.MoebiusSolutions.git_tool;

import jacle.common.io.CloseablesExt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.commons.exec.PumpStreamHandler;

public class ProcessLauncher {

	/**
	 * Static accessor.
	 */
	public static final ProcessLauncher I = new ProcessLauncher();
	
    /**
	 * Launches a process, waits for termination, verifies that the process
	 * exited with 0, and returns the process TTY.
	 */
    public String launchAndWait(Path workingDir, String... cmd) {
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    	try {
    		
	    	// Launch process
	    	ProcessBuilder processBuilder = new ProcessBuilder(cmd).directory(workingDir.toFile());
	    	processBuilder.redirectError(processBuilder.redirectOutput());
	    	Process process = processBuilder.start();
	
	    	// Bind stream handling
	    	process.getOutputStream().close();
	    	PumpStreamHandler streamPumper = new PumpStreamHandler(outputStream);
	    	streamPumper.setProcessOutputStream(process.getInputStream());
	    	streamPumper.start();

	    	// Wait for and verify process termination
	        int exitValue = process.waitFor();
	        if (exitValue != 0) {
	        	throw new IOException(String.format("Process terminated with exit [%s]", exitValue));
	        }
	        
	        // Kill threads and flush
	        streamPumper.stop();
	        
    	} catch (IOException | InterruptedException e) {
    		throw new RuntimeException(e);
		} finally {
			CloseablesExt.closeQuietly(outputStream);
    	}
        return new String(outputStream.toByteArray());
    }

}
