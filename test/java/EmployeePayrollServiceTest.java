import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

public class EmployeePayrollServiceTest {
    private static final String HOME =
            "C:/Users/Praveen Satya";
    private static final String PLAY_WITH_NIO = "TempPlayGround";

    //Method to delete files
    public boolean deleteFiles(File contentsToDelete) {
        File[] allContents = contentsToDelete.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteFiles(file);
            }
        }
        return contentsToDelete.delete();
    }

    @Test
    public void givenPathWhenCheckedThenConfirm() throws IOException {
        //Check File Exists
        Path homePath = Paths.get(HOME);
        Assert.assertTrue(Files.exists(homePath));

        // Delete file and check file not exist
        Path playPath = Paths.get(HOME + "/" + PLAY_WITH_NIO);
        if (Files.exists(playPath)) deleteFiles(playPath.toFile());
        Assert.assertTrue(Files.notExists(playPath));

        // Create Directory
        Files.createDirectory(playPath);
        Assert.assertTrue(Files.exists(playPath));

        // Create File
        IntStream.range(1, 10).forEach(cntr -> {
            Path tempFile = Paths.get(playPath + "/temp" + cntr);

            Assert.assertTrue(Files.notExists(tempFile));

            try {
                Files.createFile(tempFile);
            } catch (IOException e) {
                System.out.println("IO Exception Occured.");
            }

            Assert.assertTrue(Files.exists(tempFile));
        });

        // List files & directories
        Files.list(playPath).filter(Files::isRegularFile)
                .forEach(System.out::println);
        System.out.println();

        Files.newDirectoryStream(playPath).forEach(System.out::println);
        System.out.println();

        Files.newDirectoryStream(playPath, path -> path.toFile().isFile()
                && path.toString().startsWith("temp"))
                .forEach(System.out::println);

    }
}