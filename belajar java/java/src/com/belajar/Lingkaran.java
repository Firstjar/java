package com.belajar;
import java.util.Scanner;

public class Lingkaran {

    int jari;
    double pi = 3.14;

    void input(int jari, double pi){
        this.jari = jari;
        this.pi = pi;
    }

    double hitungLuas(){
        return pi*jari*jari;
    }

    public static void main(String[] args) {
        Scanner Input = new Scanner(System.in);

        Lingkaran lingkarann = new Lingkaran();
        System.out.print("Masukkan jari-jari : ");
        int r = Input.nextInt();
        lingkarann.input(r, 3.14);
        System.out.println("Luas lingkaran adalah "+lingkarann.hitungLuas());
    }
}
