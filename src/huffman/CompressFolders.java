/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Loujaina
 */
public class CompressFolders {

    String filename;
    Scanner fileInput;
    HashMap<Integer, String> codes;
    Tree tree = new Tree();
    FileReader fr;

    public void compDirectory(String name) throws IOException {
        File dir = new File(name);
        File[] directoryListing = dir.listFiles();
        List<ArrayNode> charfreq = new ArrayList<ArrayNode>();

        if (directoryListing != null) {
            charfreq.add(new ArrayNode(10, 0));

            for (File child : directoryListing) {
                // Do something with child

                fileInput = new Scanner(new File(child.getAbsolutePath()));
                //create a list that has all characters and their frequencies
                //add a new node for the "new line" character
                //10 is the ascii for the "new line" character
                while (fileInput.hasNext()) {
                    String line = fileInput.nextLine();
                    char currentChar;

                    for (int i = 0; i < line.length(); i++) {
                        currentChar = line.charAt(i);
                        //search in the frequency array
                        boolean found = false;
                        for (int j = 0; j < charfreq.size(); j++) {
                            //if the character is fount in the array increment its frequency
                            if (charfreq.get(j).ascii == (int) currentChar) {
                                charfreq.get(j).setFreq(charfreq.get(j).getFreq() + 1);
                                found = true;
                            }
                        }
                        //if the character is not found add it to the list and set frequency to 1
                        if (found == false) {
                            charfreq.add(new ArrayNode((int) currentChar, 1));
                        }
                    }
                    charfreq.get(0).setFreq(charfreq.get(0).getFreq() + 1);
                }
                fileInput.close();

            }
            //remove the extra newline 
            charfreq.get(0).setFreq(charfreq.get(0).getFreq() - 1);
            if (charfreq.get(0).getFreq() == 0) {
                charfreq.remove(0);
            }

            System.out.println(charfreq.size());
            for (int i = 0; i < charfreq.size(); i++) {
                System.out.println((char) charfreq.get(i).ascii + " " + charfreq.get(i).ascii + " " + charfreq.get(i).freq);
            }

            codes = tree.createCodes(charfreq);
            DefaultMutableTreeNode huffmanTree = tree.huffmanTree;
            tree.writeHeader(huffmanTree, "folderCompressed.txt");
            dir = new File(name);
            directoryListing = dir.listFiles();
            if (directoryListing != null) {
                FileWriter fw = new FileWriter("folderCompressed.txt", true);//true->appends
                BufferedWriter writer = new BufferedWriter(fw);
                BufferedReader reader;
                for (File child : directoryListing) {
                    // Do something with child
                    filename = child.getName();
                    writer.newLine();
                    writer.append("START " + filename);
                    writer.newLine();
                    System.out.println(filename);

                    fr = new FileReader(new File(child.getAbsolutePath()));
                    reader = new BufferedReader(fr);

                    boolean firstline = true;
                    String compressed = "";
                    String temp = "";
                    char current;
                    String line;
                    int n = 0;
                    String buffer = ""; //for more efficient writing to file, group a bit of bytes
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                        if (!firstline) {
                            temp = temp.concat(codes.get(10));
                        }

                        if (line.isEmpty()) {
                            temp = temp.concat(codes.get(10));
                        } else {
                            for (int i = 0; i < line.length(); i++) {
                                current = line.charAt(i);

                                temp = temp.concat(codes.get((int) current));

                                while (temp.length() > 8) {
                                    //remove byte to start using immediately
                                    compressed = temp.substring(0, 8);
                                    temp = temp.substring(8);
                                    char charc = (char) Integer.parseInt(compressed, 2);
                                    buffer = buffer.concat("" + charc);
                                    n++;

                                }
                            }
                        }
                        if (n >= 1000) {
                            writer.append(buffer);
                            buffer = ""; //empty buffer
                            n = 0;

                        }
                        firstline = false;
                    }
                    if (n > 0)//still has sth;
                    {
                        writer.append(buffer);
                    }
                    int length = temp.length();

                    while (temp.length() < 8 && temp.length() > 0) //still left with sthhh
                    {

                        temp = "0" + temp;

                    }
                    char charc = (char) Integer.parseInt(temp, 2);
                    System.out.println(charc + ": " + temp);
                    System.out.println("last was: " + length);
                    writer.append(charc);
                    writer.append("xxx" + length + "\n"); //to know how much zeros are appended
                    writer.newLine();
                    writer.append("END");
                    reader.close();
                    fr.close();

                }

                writer.close();
                fw.close();

            }

        } else {
            // Handle the case where dir is not really a directory.
            // Checking dir.isDirectory() above would not be sufficient
            // to avoid race conditions with another process that deletes
            // directories.
        }

    }
}
