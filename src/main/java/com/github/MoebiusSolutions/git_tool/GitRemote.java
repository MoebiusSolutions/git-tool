package com.github.MoebiusSolutions.git_tool;

import java.nio.file.Path;

/**
 * Represents a remote defined in a local git repo.
 * 
 * @author rkenney
 */
public class GitRemote {
	
	public GitRemote() {}

	public GitRemote(Path repoDir, String name, String url) {
		this.repoDir = repoDir;
		this.name = name;
		this.url = url;
	}
	
	public Path repoDir;
	public String name;
	public String url;
}
