/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Loujaina
 */
public class main {
    public static void main(String[] args) throws FileNotFoundException, IOException {

        CompressFolders compF=new CompressFolders();
        DecompressFolders decompF=new DecompressFolders();
        //Compressbinary compB=new Compressbinary();
        Scanner input = new Scanner(System.in);
        System.out.print("enter file name: ");
        String fileName = input.next();
        System.out.print("to Compress Folders press 1\nto decompress Folders press 2\n  ");
        int mode = input.nextInt();
        if (mode != 1 && mode != 2 && mode!=3) {
            System.out.println("please enter a valid mode!");
        }
        long startTime = System.nanoTime();

        switch (mode) {
            case 1:
                compF.compDirectory(fileName);
                break;
            case 2:
                decompF.decomp(fileName);
                break;
            //case 3:
              //  compB.compBinary(fileName);
            default:
                break;
        }
          long endTime   = System.nanoTime();
          long totalTime = endTime - startTime;
           System.out.println("\n\n\nTOTAL TIME NEEDED :"+totalTime+"ns");   

            input.close();

        }

}
