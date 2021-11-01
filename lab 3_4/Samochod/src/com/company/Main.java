package com.company;

import java.util.concurrent.atomic.AtomicBoolean;


class Samochod extends Thread {

    private String nrRej;
    private int pojZbiornika;
    private int paliwo;

    private final AtomicBoolean czyDziala = new AtomicBoolean();


    public Samochod(String nr, int _pojZbiornika, int paliwo) {
        this.nrRej = nr;
        this.pojZbiornika = _pojZbiornika;
        this.paliwo = paliwo;
    }

    public void tankowanie(int _paliwo) {
        paliwo = 100;
        System.out.println("tankowanie");

    }

    public void start() {
        super.start();
    }

    public final void stopp() {
        System.out.println("Zatrzymuje sie");
        czyDziala.set(false);
        tankowanie(paliwo);
    }

    public void uruchom() throws InterruptedException {
        czyDziala.set(true);
        int iter = 0;

        while (czyDziala.get()) {

            Thread.sleep(1500);

            System.out.println("Jedziemy samochodem " + nrRej + " i mamy " + paliwo + "paliwa");
            paliwo -= 1;
            iter++;
            if (paliwo == 20) {
                System.out.println("potrzeba zatankowac");
            }
            if (paliwo == 0) {
                stopp();
            }


        }
    }

    @Override
    public void run() {
        System.out.println("Samochod nr: "+nrRej);

        try {
            uruchom();
        } catch (InterruptedException e) {
            stopp();
            e.printStackTrace();
        }
    }

}

class TestSamochod{

    public static void main(String[] args)  {
        var citroen = new Samochod("raz",100,100);
        var bmw = new Samochod("dwa",100,40);
        var audi = new Samochod("trzy",300,200);

        citroen.start();
        bmw.start();
        audi.start();

    }
}