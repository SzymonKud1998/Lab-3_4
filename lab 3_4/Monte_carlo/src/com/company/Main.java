package com.company;

import java.util.Random;

class Monte_Carlo extends Thread {
    double xStart, xStop, yStart, yStop;
    int licznik_prob;
    double wynik;
    Random losowe;

    public Monte_Carlo(double xStart, double xStop, double yStop, double yStart, int licznik) {
        this.xStart = xStart;
        this.yStart = yStart;
        this.xStop = xStop;
        this.yStop = yStop;
        this.wynik = 0;
        this.losowe = new Random();
        this.licznik_prob = licznik;

    }

    public void run() {
        int trafienie = 0;

        for (int i = 0; i < this.licznik_prob; i++) {
            double x = Math.random();
            double y = Math.random();

            if ((x * x + y * y) <= 1)
                trafienie++;
        }

        this.wynik = trafienie;
    }

    public double Wynik() {
        return this.wynik;
    }

}

public class Main {

    public static void main(String[] args) {
        Monte_Carlo w1, w2, m3, w4;
        int ilosc_prob = 1000;
        double a = 10;

        w1 = new Monte_Carlo(0,0, a/2, a/2, ilosc_prob);
        w2 = new Monte_Carlo(a/2,0, 1, a/2, ilosc_prob);
        m3 = new Monte_Carlo(0, a/2, a/2, a, ilosc_prob);
        w4 = new Monte_Carlo(a/2,a/2, a, a, ilosc_prob);

        w1.run();
        w2.run();
        m3.run();
        w4.run();

        try {
            w1.join();
            w2.join();
            m3.join();
            w4.join();
        }catch (Exception e){

        }
        double pole = w1.Wynik() + w2.Wynik() + m3.Wynik() + w4.Wynik();
        pole = pole / ((double)ilosc_prob * 4) * (a * a);
        System.out.println("Pole kola wynosi = " + pole);
    }
}