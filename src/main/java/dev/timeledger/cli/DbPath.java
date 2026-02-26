package dev.timeledger.cli;

import java.nio.file.Path;

@FunctionalInterface
public interface DbPath {
    Path dbFile();
}