git-tool.jar
====================

This is a basic java application used to manipulate git repositories.

Usage
--------------------

```
  java -jar git-tool.jar [fix-urls [--simulate] <base-directory>]

  fix-urls
    Upates the remote urls of moesol repos from the old format to the new (Dec 2015).
  --simulate
    Simulates execution without any side-effects.
  base-directory
    The base directory to search in. This may contain any number of git repos nested at any depth.

```

The following command causes the tool to search through all subdirectories of "my/files", identify git repos with "remote" urls points to the Moebius Solutions webserver in the old url format, and updates those urls.

```
java git-tool.jar fix-urls my/files
```

Adding the "--simulate" option causes the fixes to be identified, but not executed.

```
java git-tool.jar fix-urls --simulate my/files
```
