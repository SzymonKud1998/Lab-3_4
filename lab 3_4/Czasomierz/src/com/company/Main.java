package com.company;
class Czasomierz extends Thread {
    int h;
    int m;
    int s;
    public void run(){
        while(true){
            s++;
            if(h == 23 && m == 59 && s == 60){
                s = 0;
                m = 0;
                h = 0;
            }
            else if(m == 59 && s == 60){
                m = 0;
                s = 0;
                h++;
            }
            else if(s == 60){
                s = 0;
                m++;

            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.format("%02d:%02d:%02d",h,m,s);
            System.out.println();

        }
    }
}
public class Main {

    public static void main(String[] args) {
	Czasomierz c = new Czasomierz();
    c.run();
    }
}
