package us.mudkip989.mods.nbs_extensions.nbs;

public record SongData(String name, String author, float speed, int length, String notes, String fileName,
                       String layers, int loopTick, int loopCount, int customInstrumentCount,
                       String[] customInstrumentNames) {

}
