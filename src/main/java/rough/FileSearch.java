package rough;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

class FileSearch {
    private static final String[] extensions = new String[]{"java"};
    private static final String searchWord = "ChromeDriverService";
    //    private static final String path = System.getProperty("user.dir");
    private static final String path = "F:\\ABD";

    public static void main(String[] args) throws IOException {
        Collection<File> files = FileUtils.listFiles(Paths.get(path).toFile(), extensions, true);
        for (File file : files) {
            if (StringUtils.containsIgnoreCase(Files.readString(file.toPath()), searchWord)) {
                System.out.println(file.getAbsolutePath());
            }
        }
    }
}