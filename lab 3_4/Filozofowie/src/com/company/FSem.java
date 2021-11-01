package com.company;
import java.util.concurrent.Semaphore;

public class FSem implements Filozofowie {
    private int iloscFilozofow;

    FSem(int ilosc) {
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

class FSemafora extends Thread {
    public static Semaphore[] widelce;
    private int mojNum;

    public FSemafora(int nr) {
        mojNum = nr;
    }

    public static void iloscWidelcow(int ilosc) {
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


    private void czekaj() {
        try {
            Thread.sleep((long) (5000 * Math.random()));
        } catch (InterruptedException e) {
        }
    }

    private void podniesWidelce() {
        int widelecLewy = mojNum;
        int widelecPrawy = (mojNum + 1) % widelce.length;
        widelce[widelecLewy].acquireUninterruptibly();
        widelce[widelecPrawy].acquireUninterruptibly();
    }

    private void odlozWidelce() {
        int widelecLewy = mojNum;
        int widelecPrawy = (mojNum + 1) % widelce.length;
        widelce[widelecLewy].release();
        widelce[widelecPrawy].release();
    }
}