package us.mudkip989.mods.nbs_extensions.sys;

import java.nio.file.Path;

public enum ExternalFile {
    NBS_FILES(builder()
            .isDirectory(true)
            .setName("NBS Files")
            .build());

    private final Path path;

    ExternalFile(Path path) {
        this.path = path;
    }

    public static ExternalFileBuilder builder() {
        return new ExternalFileBuilder();
    }

    public Path getPath() {
        return path;
    }
}
