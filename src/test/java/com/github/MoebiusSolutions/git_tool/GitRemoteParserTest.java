package com.github.MoebiusSolutions.git_tool;

import static org.junit.Assert.*;
import jacle.common.lang.Handler;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GitRemoteParserTest {

	@Test
	public void test() throws Exception {
		MockHandler mockHandler = new MockHandler();
		GitRemoteParser parser = new GitRemoteParser(mockHandler);
		parser.handle(
				new GitRemoteTty(Paths.get("mock-dir"),
					"origin	https://github.com/MoebiusSolutions/jacle.git (fetch)\n"+
					"origin	https://github.com/MoebiusSolutions/jacle.git (push)\n"));
		
		assertEquals(1, mockHandler.handled.size());
		assertEquals(Paths.get("mock-dir"), mockHandler.handled.get(0).repoDir);
		assertEquals("origin", mockHandler.handled.get(0).name);
		assertEquals("https://github.com/MoebiusSolutions/jacle.git", mockHandler.handled.get(0).url);
	}
	
	private static final class MockHandler implements Handler<GitRemote> {

		List<GitRemote> handled = new ArrayList<GitRemote>();
		
		@Override
		public void handle(GitRemote item) {
			handled.add(item);
		}
	}
}
