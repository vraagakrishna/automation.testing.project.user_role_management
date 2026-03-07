package utils;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Comparator;

public class FileUtils {

    public static void saveAndAttachHtml(String html, String label) throws Exception {
        File latestReportFolder = getLatestReportFolder();

        String filesDir = latestReportFolder.getAbsolutePath() + "/files/";

        File folder = new File(filesDir);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String fileName = label.replace(" ", "_") + "_" + System.currentTimeMillis() + ".html";
        File file = new File(folder, fileName);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(html);
        }

        String relativePath = "../files/" + fileName;

        String htmlLink = "<a href='" + relativePath + "' target='_blank'>" + label + "</a>";

        ExtentCucumberAdapter.addTestStepLog("HTML saved: " + htmlLink);
    }

    private static File getLatestReportFolder() throws IOException {
        File baseDir = new File("ExtentReports");

        return Files.list(baseDir.toPath())
                    .map(java.nio.file.Path::toFile)
                    .filter(File::isDirectory)
                    .filter(file -> file.getName()
                                        .startsWith("Report_"))
                    .max(Comparator.comparingLong(File::lastModified))
                    .orElse(null);
    }

}
