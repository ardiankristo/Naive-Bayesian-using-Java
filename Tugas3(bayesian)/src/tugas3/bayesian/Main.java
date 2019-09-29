/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tugas3.bayesian;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author ardian
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    static String dataset[][] = readFile("D:\\KULIAH\\SMT 5\\Machine Learning (Pak Ali Ridho)\\Tugas3(bayesian)\\bayesian.csv");

    public static void main(String[] args) {
        System.out.println("LIST SAMPLE DATA");
        for (int i = 0; i < dataset.length; i++) {
            for (int j = 0; j < dataset[i].length; j++) {
                System.out.printf(dataset[i][j] + "\t");
            }
            System.out.println("");
        }
        String[] testData = new String[3];
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter 0 for not checking the data");
        System.out.println("Apakah saya akan Enjoy Sport apabila: ");
        System.out.println("Cuaca: ");
        testData[0] = sc.nextLine();
        System.out.println("Temperatur: ");
        testData[1] = sc.nextLine();
        System.out.println("Kecepatan angin: ");
        testData[2] = sc.nextLine();

        String result = Classify(testData);
        
        System.out.println("\nHasil adalah " + result);
    }

    public static String Classify(String[] testData) {
        int classifierIndex = testData.length, indexDataKosong = 0;
        boolean[] berisi = new boolean[testData.length];
        double[] yaX1 = new double[testData.length], tidakX1 = new double[testData.length];
        double sumYa = 0, sumTidak = 0, NBya = 1, NBtidak = 1;
        String hasil = "wow";

        for (int i = 0; i < dataset.length; i++) {
            if (dataset[i][classifierIndex].equals("ya")) {
                sumYa++;
            } else if (dataset[i][classifierIndex].equals("tidak")) {
                sumTidak++;
            }
            for (int j = 0; j < dataset[i].length - 1; j++) {
                if (testData[j].equals("0")) {
                    berisi[j] = false;
                }
                else {
                    berisi[j] = true;
                }
                if (testData[j] != "0") {
                    if (testData[j].equals(dataset[i][j]) && dataset[i][classifierIndex].equals("ya")) {
                        yaX1[j]++;
                    } else if (testData[j].equals(dataset[i][j]) && dataset[i][classifierIndex].equals("tidak")) {
                        tidakX1[j]++;
                    } else {

                    }
                } else {

                }
            }
        }
        System.out.println("\nJumlah ya = " + sumYa);
        for (int i = 0; i < yaX1.length; i++) {
            if (yaX1[i] != 0) {
                yaX1[i] = yaX1[i] / sumYa;
            }
            System.out.println("P data[" + i + "] ya = " + yaX1[i]);
        }
        double pYa = sumYa / dataset.length;
        System.out.println("P ya= " + pYa);
        
        for (int i = 0; i < yaX1.length; i++) {
            if (berisi[i] == true) {
                NBya *= yaX1[i];
            }
        }
        NBya = NBya * pYa;

        System.out.println("Naive Bayesian untuk ya = " + NBya);

        System.out.println("\nJumlah tidak = " + sumTidak);
        for (int i = 0; i < tidakX1.length; i++) {
            if (tidakX1[i] != 0) {
                tidakX1[i] = tidakX1[i] / sumTidak;
            }
            System.out.println("P data[" + i + "] tidak = " + tidakX1[i]);
        }
        double pTidak = sumTidak / dataset.length;
        System.out.println("P tidak= " + pTidak);

        for (int i = 0; i < tidakX1.length; i++) {
            if (berisi[i] == true) {
                NBtidak *= tidakX1[i];
            }
        }
        NBtidak *= pTidak;

        System.out.println("Naive Bayesian untuk tidak = " + NBtidak);
        
        if (NBya > NBtidak) {
            hasil = "ya";
        } else if (NBya < NBtidak) {
            hasil = "tidak";
        } else if (NBya == NBtidak && NBtidak == 0) {
            hasil = "tidak tahu karena keduanya 0";
        } else if (NBya == NBtidak) {
            hasil = "tidak tahu karena keduanya sama";
        } else {
            System.out.println(NBya + " " + NBtidak);
        }
        return hasil;
    }

    public static String[][] readFile(String fileName) {
        String dataset[][] = new String[6][4];
        StringTokenizer st;
        try {
            FileReader file = new FileReader(fileName);
            BufferedReader br = new BufferedReader(file);
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                st = new StringTokenizer(line, ",");
                for (int j = 0; j < dataset[0].length; j++) {
                    dataset[i][j] = st.nextToken();
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataset;
    }

}
