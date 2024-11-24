package us.mudkip989.mods.nbs_extensions.util;

import us.mudkip989.mods.nbs_extensions.nbs.*;
import us.mudkip989.mods.nbs_extensions.sys.*;

import java.nio.file.*;
import java.util.*;

public class CacheManager {

    private Path conf;

    private HashMap<String, SongCacheData> Cache;

    public CacheManager(){

        conf = new ExternalFileBuilder().setName("cache.json").isDirectory(false).build();

    }



}
