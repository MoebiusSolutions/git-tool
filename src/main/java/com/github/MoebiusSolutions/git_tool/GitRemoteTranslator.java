package com.github.MoebiusSolutions.git_tool;

import jacle.common.lang.Handler;

/**
 * Translates the url of incoming {@link GitRemote}s and then passes the result
 * to the provided {@link Handler}.
 * 
 * @author rkenney
 */
public class GitRemoteTranslator implements Handler<GitRemote>{

	private Handler<GitRemoteTranslation> handler;

	public GitRemoteTranslator(Handler<GitRemoteTranslation> newRemoteHandler) {
		this.handler = newRemoteHandler;
	}
	
	@Override
	public void handle(GitRemote exitingRemote) {
		String newUrl = MoesolUrlConverter.I.toNewUrl(exitingRemote.url);
		if (newUrl == null) {
			return;
		}
		GitRemote newRemote = new GitRemote(
				exitingRemote.repoDir,
				exitingRemote.name,
				newUrl);
		handler.handle(new GitRemoteTranslation(exitingRemote, newRemote));
	}

}
