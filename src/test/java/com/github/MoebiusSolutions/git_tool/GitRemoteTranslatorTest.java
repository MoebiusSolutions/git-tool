package com.github.MoebiusSolutions.git_tool;

import static org.junit.Assert.assertEquals;
import jacle.common.lang.Handler;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GitRemoteTranslatorTest {

	@Test
	public void test_IrrelevantUrl() throws Exception {
		MockHandler mockHandler = new MockHandler();
		GitRemoteTranslator translator = new GitRemoteTranslator(mockHandler);
		
		GitRemote input = new GitRemote(Paths.get("mock-dir"), "mock name", "https://github.com/MoebiusSolutions/jacle.git");
		translator.handle(input);
		assertEquals(0, mockHandler.handled.size());
	}
	
	@Test
	public void test_ConvertSshUrlWithUsername() throws Exception {
		MockHandler mockHandler = new MockHandler();
		GitRemoteTranslator translator = new GitRemoteTranslator(mockHandler);
		
		GitRemote input = new GitRemote(Paths.get("mock-dir"), "mock name", "ssh://rkenney@git.moesol.com/git/otm.git");
		translator.handle(input);
		assertEquals(1, mockHandler.handled.size());
		GitRemote oldRemote = mockHandler.handled.get(0).oldRemote;
		assertEquals(Paths.get("mock-dir"), oldRemote.repoDir);
		assertEquals("mock name", oldRemote.name);
		assertEquals("ssh://rkenney@git.moesol.com/git/otm.git", oldRemote.url);
		GitRemote newRemote = mockHandler.handled.get(0).newRemote;
		assertEquals(Paths.get("mock-dir"), newRemote.repoDir);
		assertEquals("mock name", newRemote.name);
		assertEquals("ssh://rkenney@git.moesol.com/otm.git", newRemote.url);
	}
	
    @Test
    public void test_ConvertSshUrlWithoutUsername() throws Exception {
        MockHandler mockHandler = new MockHandler();
        GitRemoteTranslator translator = new GitRemoteTranslator(mockHandler);
        
        GitRemote input = new GitRemote(Paths.get("mock-dir"), "mock name", "ssh://git.moesol.com/git/otm.git");
        translator.handle(input);
        assertEquals(1, mockHandler.handled.size());
        GitRemote oldRemote = mockHandler.handled.get(0).oldRemote;
        assertEquals(Paths.get("mock-dir"), oldRemote.repoDir);
        assertEquals("mock name", oldRemote.name);
        assertEquals("ssh://git.moesol.com/git/otm.git", oldRemote.url);
        GitRemote newRemote = mockHandler.handled.get(0).newRemote;
        assertEquals(Paths.get("mock-dir"), newRemote.repoDir);
        assertEquals("mock name", newRemote.name);
        assertEquals("ssh://git.moesol.com/otm.git", newRemote.url);
    }
    
    @Test
    public void test_ConvertSshUrlWithoutUsernameWithoutSshQualifier() throws Exception {
        MockHandler mockHandler = new MockHandler();
        GitRemoteTranslator translator = new GitRemoteTranslator(mockHandler);
        
        GitRemote input = new GitRemote(Paths.get("mock-dir"), "mock name", "git.moesol.com/git/otm.git");
        translator.handle(input);
        assertEquals(1, mockHandler.handled.size());
        GitRemote oldRemote = mockHandler.handled.get(0).oldRemote;
        assertEquals(Paths.get("mock-dir"), oldRemote.repoDir);
        assertEquals("mock name", oldRemote.name);
        assertEquals("git.moesol.com/git/otm.git", oldRemote.url);
        GitRemote newRemote = mockHandler.handled.get(0).newRemote;
        assertEquals(Paths.get("mock-dir"), newRemote.repoDir);
        assertEquals("mock name", newRemote.name);
        assertEquals("ssh://git.moesol.com/otm.git", newRemote.url);
    }
    
    @Test
    public void test_ConvertSshUrlWithUsernameWithoutSshQualifier() throws Exception {
        MockHandler mockHandler = new MockHandler();
        GitRemoteTranslator translator = new GitRemoteTranslator(mockHandler);
        
        GitRemote input = new GitRemote(Paths.get("mock-dir"), "mock name", "rkenney@git.moesol.com/git/otm.git");
        translator.handle(input);
        assertEquals(1, mockHandler.handled.size());
        GitRemote oldRemote = mockHandler.handled.get(0).oldRemote;
        assertEquals(Paths.get("mock-dir"), oldRemote.repoDir);
        assertEquals("mock name", oldRemote.name);
        assertEquals("rkenney@git.moesol.com/git/otm.git", oldRemote.url);
        GitRemote newRemote = mockHandler.handled.get(0).newRemote;
        assertEquals(Paths.get("mock-dir"), newRemote.repoDir);
        assertEquals("mock name", newRemote.name);
        assertEquals("ssh://rkenney@git.moesol.com/otm.git", newRemote.url);
    }
    
    @Test
    public void test_ConvertSshUrlWithColon() throws Exception {
        MockHandler mockHandler = new MockHandler();
        GitRemoteTranslator translator = new GitRemoteTranslator(mockHandler);
        
        GitRemote input = new GitRemote(Paths.get("mock-dir"), "mock name", "ssh://git.moesol.com:/git/otm.git");
        translator.handle(input);
        assertEquals(1, mockHandler.handled.size());
        GitRemote oldRemote = mockHandler.handled.get(0).oldRemote;
        assertEquals(Paths.get("mock-dir"), oldRemote.repoDir);
        assertEquals("mock name", oldRemote.name);
        assertEquals("ssh://git.moesol.com:/git/otm.git", oldRemote.url);
        GitRemote newRemote = mockHandler.handled.get(0).newRemote;
        assertEquals(Paths.get("mock-dir"), newRemote.repoDir);
        assertEquals("mock name", newRemote.name);
        assertEquals("ssh://git.moesol.com/otm.git", newRemote.url);
    }
    
	private static final class MockHandler implements Handler<GitRemoteTranslation> {

		List<GitRemoteTranslation> handled = new ArrayList<GitRemoteTranslation>();
		
		@Override
		public void handle(GitRemoteTranslation item) {
			handled.add(item);
		}
	}
}
