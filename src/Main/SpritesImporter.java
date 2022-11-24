package Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**This class is used for importing sprites based on a URL*/

public class SpritesImporter {

    public BufferedImage importSpriteSheet(String fileName){
        BufferedImage img = null;
        try {
            InputStream stream = getClass().getResourceAsStream(fileName);
            assert stream != null;
            img = ImageIO.read(stream);
        } catch (IOException e) {
            System.out.println("Error: File not found " + fileName);
        }
        return img;
    }
}
