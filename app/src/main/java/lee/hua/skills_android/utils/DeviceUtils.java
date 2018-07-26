package lee.hua.skills_android.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

import java.io.File;

public class DeviceUtils {
    public static String getRomSpace(Context context) {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockCount = stat.getBlockCountLong();
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();

        String totalSize = Formatter.formatFileSize(context, blockCount * blockSize);
        String availableSize = Formatter.formatFileSize(context, availableBlocks * blockSize);

        return "total Rom -> " + totalSize + "\n" +
                "available Rom -> " + availableSize;
    }
}
