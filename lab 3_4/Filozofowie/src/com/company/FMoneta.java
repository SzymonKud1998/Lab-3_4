package com.company;
import java.util.Random;
import java.util.concurrent.Semaphore;


public class FMoneta implements Filozofowie {
    private int iloscFilozofow;

    FMoneta(int ilosc) {
        iloscFilozofow = ilosc;
    }

    @Override
    public void uruchomProblem() {
        FSemafora.iloscWidelcow(iloscFilozofow);

        for (int i = 0; i < iloscFilozofow; i++) {
            new FSemafora(i).start();
        }
    }
}

class FM extends Thread {
    public static Semaphore[] widelce;
    private int mojNum;
    private Random losuj = new Random();

    public FM(int nr) {
        mojNum = nr;
    }

    public static void usawIlosWidelcow(int ilosc) {
        widelce = new Semaphore[ilosc];
        for (int i = 0; i < widelce.length; i++)
            widelce[i] = new Semaphore(1);
    }

    public void run() {
        while (true) {
            System.out.println("Muszę pomyśleć: " + mojNum);
            czekaj();

            podniesWidelce();
            System.out.println("Jem: " + mojNum);
            czekaj();

            System.out.println("Skończyłem: " + mojNum);
            odlozWidelce();
        }
    }

    private void podniesWidelce() {
        int widelecLewy = mojNum;
        int widelecPrawy = (mojNum + 1) % widelce.length;

        boolean najpierwLewy = losuj.nextBoolean();
        boolean podnioslDwaWidelce = false;
        do {
            if (najpierwLewy)
                podnioslDwaWidelce = coPodniesc(widelecLewy, widelecPrawy);
            else
                podnioslDwaWidelce = coPodniesc(widelecPrawy, widelecLewy);
        } while (!podnioslDwaWidelce);
    }

    private boolean coPodniesc(int najpierw, int potem) {
        widelce[najpierw].acquireUninterruptibly();

        if (!widelce[potem].tryAcquire()) {
            widelce[najpierw].release();
        } else {
            return true;
        }

        return false;
    }

    private void odlozWidelce() {
        int widelecLewy = mojNum;
        int widelecPrawy = (mojNum + 1) % widelce.length;
        widelce[widelecLewy].release();
        widelce[widelecPrawy].release();
    }

    private void czekaj() {
        try {
            Thread.sleep((long) (5000 * Math.random()));
        } catch (InterruptedException e) {
        }
    }
}