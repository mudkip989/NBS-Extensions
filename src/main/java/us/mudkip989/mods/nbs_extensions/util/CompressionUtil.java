package us.mudkip989.mods.nbs_extensions.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.GZIPOutputStream;

public class CompressionUtil {

    public static byte[] toBase64(byte[] bytes) {
        return Base64.getEncoder().encode(bytes);
    }

    public static byte[] toGZIP(byte[] bytes) throws IOException {
        if (bytes == null) {
            return null;
        }
        ByteArrayOutputStream obj = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(obj);
        gzip.write(bytes);
        gzip.close();

        return obj.toByteArray();
    }

}