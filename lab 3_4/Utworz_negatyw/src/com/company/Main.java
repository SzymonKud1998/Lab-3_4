package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Negatyw extends Thread{
    BufferedImage pobierz_obraz;
    int xStart, yStart, xStop, yStop;

    public Negatyw( BufferedImage obraz, int x_start, int x_stop, int y_stop, int y_start ){
        this.xStart = x_start;
        this.yStart = y_start;
        this.xStop = x_stop;
        this.yStop = y_stop;
        this.pobierz_obraz = obraz;
    }

    @Override
    public void run() {
        for(int i = xStart; i < xStop; i++){
            for(int j = yStart; j < yStop; j++) {
                Color c = new Color(pobierz_obraz.getRGB(i, j));
                int red = c.getRed();
                int green = c.getGreen();
                int black = c.getBlue();
                int R, G, B;
                R = 255 - red;
                G = 255 - green;
                B = 255 - black;
                Color newColor = new Color(R, G, B);
                pobierz_obraz.setRGB(i, j, newColor.getRGB());
            }
        }
    }

}

class Main {

    public static void main(String[] args) throws IOException {
        BufferedImage image;

        File input = new File("zdj.jpg");
        image = ImageIO.read(input);
        int szerokosc = image.getWidth();
        int wysokosc = image.getHeight();
        int pole_szerokosci = szerokosc / 2;
        int pole_wysokosc = wysokosc / 2;

        Negatyw watek1, watek2, watek3, watek4;
        watek1 = new Negatyw(image, 0, 0, pole_szerokosci, pole_wysokosc);
        watek2 = new Negatyw(image, pole_szerokosci, 0, szerokosc, pole_wysokosc);
        watek3 = new Negatyw(image, 0, pole_wysokosc, pole_szerokosci, wysokosc);
        watek4 = new Negatyw(image, pole_szerokosci, pole_wysokosc, szerokosc, wysokosc);

        watek1.start();
        watek2.start();
        watek3.start();
        watek4.start();

        try {
            watek1.join();
            watek2.join();
            watek3.join();
            watek4.join();
        } catch (Exception e) { }
        File ouptut = new File("zdj_negatyw.jpg");
        ImageIO.write(image, "jpg", ouptut);
    }
}