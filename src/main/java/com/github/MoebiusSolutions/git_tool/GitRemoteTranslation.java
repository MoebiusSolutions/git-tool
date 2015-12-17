package com.github.MoebiusSolutions.git_tool;

/**
 * Represents a translation between remote definitions in a local repo.
 * 
 * @author rkenney
 */
public class GitRemoteTranslation {

	public GitRemoteTranslation(GitRemote oldRemote, GitRemote newRemote) {
		this.oldRemote = oldRemote;
		this.newRemote = newRemote;
	}
	
	public GitRemote oldRemote;
	public GitRemote newRemote;

}
