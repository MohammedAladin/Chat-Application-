package org.Client.Service;

import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;

public class ImageServices {
    public static byte[] convertToByte(BufferedImage image) {

        // Convert the BufferedImage to a byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", baos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        byte[] imageInByte = baos.toByteArray();
        return imageInByte;
    }

public static Image convertToImage(byte[] imageData) {
    BufferedImage bufferedImage = null;
    try {
        bufferedImage = ImageIO.read(new ByteArrayInputStream(imageData));
    } catch (IOException e) {
        e.printStackTrace();
    }
    return SwingFXUtils.toFXImage(bufferedImage,null);
}

}
