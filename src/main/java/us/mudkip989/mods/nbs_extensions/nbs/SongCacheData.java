package us.mudkip989.mods.nbs_extensions.nbs;

public class SongCacheData {

    private String Title, Author;
    private Integer Length;
    private Long LastModified;


    public SongCacheData(String title, String author, Long lastModified, int length){

        Title = title;
        Author = author;
        Length = length;
        LastModified = lastModified;

    }


}
