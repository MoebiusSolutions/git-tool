package com.github.MoebiusSolutions.git_tool;

import jacle.common.lang.Handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Converts "git remote -v" output to {@link GitRemote} objects, and passes
 * these to the provideded {@link Handler}.
 * 
 * @author rkenney
 */
public class GitRemoteParser implements Handler<GitRemoteTty> {

	private static final Pattern remotePattern = Pattern.compile("^(\\w+)\t([^\t]+)\\s\\(fetch\\)$", Pattern.MULTILINE);
	
	private Handler<GitRemote> handler;

	public GitRemoteParser(Handler<GitRemote> remoteHandler) {
		this.handler = remoteHandler;
	}
	
	@Override
	public void handle(GitRemoteTty gitOutput) {
		Matcher matcher = remotePattern.matcher(gitOutput.tty);
		while (matcher.find()) {
			GitRemote remote = new GitRemote();
			remote.repoDir = gitOutput.repoDir;
			remote.name = matcher.group(1); 
			remote.url = matcher.group(2);
			handler.handle(remote);
		}
	}
}
