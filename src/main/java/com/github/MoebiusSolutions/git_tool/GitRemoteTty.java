package com.github.MoebiusSolutions.git_tool;

import java.nio.file.Path;

/**
 * Represents the output of "git remote -v" on a local repo directory.
 * 
 * @author rkenney
 */
public class GitRemoteTty {

	public GitRemoteTty(Path repoDir, String tty) {
		this.repoDir = repoDir;
		this.tty = tty;
	}
	
	public Path repoDir;
	public String tty;

}
