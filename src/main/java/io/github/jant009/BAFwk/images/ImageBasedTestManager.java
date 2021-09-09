package io.github.jant009.BAFwk.images;

import io.github.jant009.BAFwk.setup.Settings;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;
import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Assertions;
import org.im4java.core.CompareCmd;
import org.im4java.core.IMOperation;
import org.im4java.process.StandardStream;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by jeremy.charpentier on 19/05/2017.
 */
public class ImageBasedTestManager {
    public final static String REFERENCE_STORAGE_DIR = Settings.getProperty("images.storage_directories.reference");
    public final static String CAPTURED_STORAGE_DIR = Settings.getProperty("images.storage_directories.captured");


    public static void captureScreenshot(PageObject page, String elementName, WebElementFacade element) {
        element.waitUntilVisible();
        try {
            int eltX = element.getLocation().getX();
            int eltY = element.getLocation().getY();
            int eltW = element.getSize().getWidth();
            int eltH = element.getSize().getHeight();

            File fscr = ((TakesScreenshot)page.getDriver()).getScreenshotAs(OutputType.FILE);
            BufferedImage image = ImageIO.read(fscr);
            BufferedImage croppedImage = image.getSubimage(eltX,eltY,eltW,eltH);
            ImageIO.write(croppedImage, "png", fscr);

            try {
                // FIXME : Should add a run identifier as directory to store all related screenshots
                FileUtils.forceDelete(new File(CAPTURED_STORAGE_DIR + File.separator + elementName + ".png"));
            } catch (FileNotFoundException e) {
                // do nothing
            }
            // FIXME : Should add a run identifier as directory to store all related screenshots
            FileUtils.moveFile(fscr, new File(CAPTURED_STORAGE_DIR + File.separator + elementName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void compareScreenshotWithReference(String elementName) {
        try {
            File refFile = new File(REFERENCE_STORAGE_DIR + File.separator + elementName + ".png");
            File capFile = new File(CAPTURED_STORAGE_DIR + File.separator + elementName + ".png");
            File diffFile = new File(CAPTURED_STORAGE_DIR + File.separator + elementName + ".diff.png");

            if (! refFile.exists()) {
                FileUtils.copyFile(capFile, refFile);
                // DONE : should provoke FAILURE
                Assertions.fail("No reference image found for element " + elementName);
            }
            // DONE : Perform the comparison
            CompareCmd cmpCmd = new CompareCmd();
            cmpCmd.setErrorConsumer(StandardStream.STDERR);

            IMOperation cmpOp = new IMOperation();
            // TODO : Define what mae stands for
            cmpOp.metric("mae");
            cmpOp.addImage(refFile.getAbsolutePath());
            cmpOp.addImage(capFile.getAbsolutePath());
            cmpOp.addImage(diffFile.getAbsolutePath());

            try {
                cmpCmd.run(cmpOp);
            }
            catch (Exception ex) {
                Assertions.fail("Observed image doesn't match the expected one for element " + elementName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
