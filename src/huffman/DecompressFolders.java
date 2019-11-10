/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Loujaina
 */
public class DecompressFolders extends JFrame {

    DefaultMutableTreeNode tree;
    String line;
    FileReader fr;
    BufferedReader reader;
    String header;
    FileWriter fw;
    BufferedWriter writer;

    //Step 1: reconstruct the tree
    public void decomp(String fileName) throws FileNotFoundException, IOException {
        fr = new FileReader(fileName); //CHANGE
        reader = new BufferedReader(fr);
        header = "";
        boolean headerDone = false;
        boolean firstline = true;
        //FIRST-> make tree
        while ((line = reader.readLine()) != null && !headerDone) {
            if (!firstline) {
                line = "\n" + line;
            }
            firstline = false;
            //deal with header to create tree
            if (line.isEmpty()) {
                line = line + "\n";
            }
            if (line.contains("START"))//end of header
            {
                headerDone = true;
                line = line.replace("START\n", ""); //remove start

            }

            header = header + line;
            header = " " + header;
            if (headerDone) {
                break;
            }
        }
        tree = buildTree();
        if (tree.getChildCount() == 1) {
            tree = (DefaultMutableTreeNode) tree.getChildAt(0);
        }
        if (tree != null) {
            JTree shagara = new JTree(tree);
            add(shagara);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setTitle("Reconstruct");
            this.pack();

            this.setVisible(true);
        }

        //Second decompress
        while ((line = reader.readLine()) != null) {
            if (line.contains("START")) {
                String filename = line.replaceAll("START ", "");
                System.out.println(filename);
                fw = new FileWriter(filename);
                DefaultMutableTreeNode node;
                writer = new BufferedWriter(fw);
                String decompressed = "";
                String binary, idk;
                while (!(line = reader.readLine()).contains("END") && line!=null) {
                    System.out.println(line);
                    binary = "";
                    //if empty line
                    if (line.isEmpty()) {
                        binary = "";

                    } else if (!line.contains("xxx")) {
                        for (int i = 0; i < line.length(); i++) {
                            idk = Integer.toBinaryString((int) line.charAt(i));
                            while (idk.length() < 8) {
                                idk = "0" + idk;
                            }
                            binary = binary + idk;
                        }
                    } else {
                        line = line.replace("xxx", "");
                        int number = Integer.parseInt("" + line.charAt(line.length() - 1));
                        number = 8 - number; //number of extra zeros
                        line = line.substring(0, line.length() - 1);//remove number
                        for (int i = 0; i < line.length() - 1; i++) {
                            idk = Integer.toBinaryString((int) line.charAt(i));
                            while (idk.length() < 8) {
                                idk = "0" + idk;
                            }
                            binary = binary + idk;
                        }
                        String temp = Integer.toBinaryString((int) line.charAt(line.length() - 1));
                        while (temp.length() < 8) {
                            temp = "0" + temp;
                        }
                        while (number > 0) {
                            temp = temp.substring(1);
                            number--;
                        }
                        binary = binary + temp;
                    }
                    node = tree;
                    for (int i = 0; i < binary.length(); i++) {
                        if (binary.charAt(i) == '1') {
                            node = (DefaultMutableTreeNode) node.getChildAt(1);
                        } else if (binary.charAt(i) == '0') {
                            node = (DefaultMutableTreeNode) node.getChildAt(0);
                        }

                        if (node.isLeaf()) {
                            if (node.toString() == "newLine") {
                                writer.append(decompressed);
                                writer.newLine();
                                decompressed = "";
                            } else {
                                decompressed = decompressed + node.toString();

                            }
                            node = tree;

                        }
                    }

                    if (decompressed.length() > 50) {
                        writer.append(decompressed);

                        decompressed = "";

                    }

                }
                if (decompressed.length() > 0) {
                    writer.append(decompressed);
                }
                writer.close();
                fw.close();

            }
           
        }
           reader.close();
            fr.close();
            System.out.println("DONE!");
    }

    public DefaultMutableTreeNode buildTree() {

        if (header.length() > 1) {
            header = header.substring(1);

            if (header.charAt(0) == '1') {
                header = header.substring(1);
                if (header.charAt(0) == '\n') {
                    return new DefaultMutableTreeNode("newLine");
                } else {
                    return new DefaultMutableTreeNode(header.charAt(0));
                }
            } else {
                DefaultMutableTreeNode left = buildTree();
                DefaultMutableTreeNode right = buildTree();
                DefaultMutableTreeNode parent = new DefaultMutableTreeNode(0);
                if (left != null) {
                    if (left.toString() == "0" && !left.isLeaf()) {
                        parent.add(left);
                    } else if (left.toString() != "0") {
                        parent.add(left);
                    }

                }
                if (right != null) {
                    if (right.toString() == "0" && !right.isLeaf()) {
                        parent.add(right);
                    } else if (right.toString() != "0") {
                        parent.add(right);
                    }
                }
                if (parent.isLeaf()) {
                    return null;
                }
                return parent;

            }
        }
        return null;
    }
}
