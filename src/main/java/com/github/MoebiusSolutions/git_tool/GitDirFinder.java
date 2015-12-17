package com.github.MoebiusSolutions.git_tool;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class GitDirFinder extends SimpleFileVisitor<Path> {

	private List<Path> repoDirs = new ArrayList<Path>();

	public List<Path> getRepoDirs() {
		return repoDirs;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
		if (".git".equals(dir.toFile().getName())) {
			repoDirs.add(dir.getParent());
			return FileVisitResult.SKIP_SUBTREE;
		} else {
			return FileVisitResult.CONTINUE;
		}
	}
}
