package com.github.MoebiusSolutions.git_tool;

import jacle.common.lang.Handler;

import java.io.PrintStream;

public class GitRemoteUpdater implements Handler<GitRemoteTranslation>{

	private PrintStream printer;
	private boolean doSimulate;
	
	public GitRemoteUpdater(PrintStream printer)	{
		this.printer = printer;
	}
	
	public GitRemoteUpdater setSimulate(boolean doSimulate) {
		this.doSimulate = doSimulate;
		return this;
	}
	
	@Override
	public void handle(GitRemoteTranslation translation) {
		// Describe action
		printer.printf("[%s] (%s) \"%s\" -> \"%s\"%n",
				translation.newRemote.repoDir,
				translation.newRemote.name,
				translation.oldRemote.url,
				translation.newRemote.url);
	
		// Stop if simulating
		if (doSimulate) {
			return;
		}
		
		// Update git repo
		try {
			ProcessLauncher.I.launchAndWait(translation.newRemote.repoDir, 
					"git", "remote", "set-url", translation.newRemote.name, translation.newRemote.url);
		} catch (Exception e) {
			printer.printf("FAILED!%n");
		}
		
//		handler.append("pushd "+newRemote.repoDir).append(System.lineSeparator());
//		handler.append(String.format("git remote set-url \"%s\" \"%s\"", newRemote.name, newRemote.url)).append(System.lineSeparator());
//		handler.append("popd").append(System.lineSeparator());
	}
}
