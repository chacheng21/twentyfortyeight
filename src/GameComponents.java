import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameComponents {
    public static BufferedImage blankBoard() {
        try {
            BufferedImage board = ImageIO.read(new File("files/BlankBoard.png"));
            return board;
        } catch (IOException e) {
            System.out.println("IO Exception Failure");
            return null;
        }
    }
    
    public static BufferedImage tile2() {
        try {
            BufferedImage tile = ImageIO.read(new File("files/Tile2.png"));
            return tile;
        } catch (IOException e) {
            System.out.println("IO Exception Failure");
            return null;
        }
    }
    
    public static BufferedImage tile4() {
        try {
            BufferedImage tile = ImageIO.read(new File("files/Tile4.png"));
            return tile;
        } catch (IOException e) {
            System.out.println("IO Exception Failure");
            return null;
        }
    }
    
    public static BufferedImage tile8() {
        try {
            BufferedImage tile = ImageIO.read(new File("files/Tile8.png"));
            return tile;
        } catch (IOException e) {
            System.out.println("IO Exception Failure");
            return null;
        }
    }
    
    public static BufferedImage tile16() {
        try {
            BufferedImage tile = ImageIO.read(new File("files/Tile16.png"));
            return tile;
        } catch (IOException e) {
            System.out.println("IO Exception Failure");
            return null;
        }
    }
    
    public static BufferedImage tile32() {
        try {
            BufferedImage tile = ImageIO.read(new File("files/Tile32.png"));
            return tile;
        } catch (IOException e) {
            System.out.println("IO Exception Failure");
            return null;
        }
    }
    
    public static BufferedImage tile64() {
        try {
            BufferedImage tile = ImageIO.read(new File("files/Tile64.png"));
            return tile;
        } catch (IOException e) {
            System.out.println("IO Exception Failure");
            return null;
        }
    }
    
    public static BufferedImage tile128() {
        try {
            BufferedImage tile = ImageIO.read(new File("files/Tile128.png"));
            return tile;
        } catch (IOException e) {
            System.out.println("IO Exception Failure");
            return null;
        }
    }
    
    public static BufferedImage tile256() {
        try {
            BufferedImage tile = ImageIO.read(new File("files/Tile256.png"));
            return tile;
        } catch (IOException e) {
            System.out.println("IO Exception Failure");
            return null;
        }
    }
    
    public static BufferedImage tile512() {
        try {
            BufferedImage tile = ImageIO.read(new File("files/Tile512.png"));
            return tile;
        } catch (IOException e) {
            System.out.println("IO Exception Failure");
            return null;
        }
    }
    
    public static BufferedImage tile1024() {
        try {
            BufferedImage tile = ImageIO.read(new File("files/Tile1024.png"));
            return tile;
        } catch (IOException e) {
            System.out.println("IO Exception Failure");
            return null;
        }
    }
    
    public static BufferedImage tile2048() {
        try {
            BufferedImage tile = ImageIO.read(new File("files/Tile2048.png"));
            return tile;
        } catch (IOException e) {
            System.out.println("IO Exception Failure");
            return null;
        }
    }
}