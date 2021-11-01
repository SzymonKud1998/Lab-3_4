package com.company;
import java.util.concurrent.Semaphore;

public class FNiesym implements Filozofowie {
    private int iloscFilozofow;

    FNiesym(int ilosc) {
        iloscFilozofow = ilosc;
    }

    @Override
    public void uruchomProblem() {
        FNiesymetryczny.usawIlosWidelcow(iloscFilozofow);

        for (int i = 0; i < iloscFilozofow; i++) {
            new FNiesymetryczny(i).start();
        }
    }
}

class FNiesymetryczny extends Thread {
    public static Semaphore[] widelce;
    private int mojNum;

    public FNiesymetryczny(int nr) {
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
        if (mojNum == 0) {
            widelce[widelecPrawy].acquireUninterruptibly();
            widelce[widelecLewy].acquireUninterruptibly();
        } else {
            widelce[widelecLewy].acquireUninterruptibly();
            widelce[widelecPrawy].acquireUninterruptibly();
        }
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