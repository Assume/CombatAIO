package scripts.CombatAIO.com.base.main.utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import scripts.starfox.api.ErrorReporting;

/**
 * The Downloader class provides methods for downloading files and registering files to the native environment.
 * 
 * @author Nolan
 */
public class Downloader {

    /**
     * Downloads the file at the specified url and saves it to the specified target directory.
     * 
     * @param fileUrl         The url of the file to download.
     * @param targetDirectory The target save location.
     * @param fileName        The name of the file.
     */
    public static final void download(String fileUrl, String targetDirectory, String fileName) {
        URL website;
        File file = null;
        try {
            new File(targetDirectory).mkdirs();
            file = new File(targetDirectory + "/" + fileName);
            file.createNewFile();
            website = new URL(fileUrl);
            try (InputStream stream = website.openStream()) {
                Files.copy(stream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (MalformedURLException | FileSystemException ex) {
            ErrorReporting.report(ex);
            return;
        } catch (IOException ex) {
            ErrorReporting.report(ex);
        }
        if (file != null && file.exists() && file.isFile()) {
            System.out.println("Successfully downloaded file from: " + fileUrl);
        }
    }

    /**
     * Registers the the font file specified to the native graphics environment.
     * 
     * @param fontFile The font file to register.
     */
    public static final void registerFont(File fontFile) {
        if (fontFile == null) {
            throw new NullPointerException("File null.");
        }
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            if (ArrayUtil.contains(font.getName(), ge.getAvailableFontFamilyNames())) {
                return;
            }
            ge.registerFont(font);
        } catch (IOException | FontFormatException e) {
            ErrorReporting.report(e);
        }
    }
}
