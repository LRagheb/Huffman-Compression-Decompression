package huffman;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.PriorityQueue;

/**
 *
 * @author Loujaina
 */
public class Tree extends JFrame {

    freqComparator freq = new freqComparator();
    HashMap<Integer, String> map = new HashMap<Integer, String>();
    DefaultMutableTreeNode huffmanTree;

    public HashMap createCodes(List<ArrayNode> charfreq) {
        PriorityQueue<DefaultMutableTreeNode> nodesQueue = new PriorityQueue<DefaultMutableTreeNode>(charfreq.size(), new freqComparator());

        for (int i = 0; i < charfreq.size(); i++) {
            nodesQueue.add(new DefaultMutableTreeNode(charfreq.get(i).ascii + "=" + charfreq.get(i).freq));
        }
        DefaultMutableTreeNode x;
        DefaultMutableTreeNode y;
        DefaultMutableTreeNode z;
        for (int i = 1; i < charfreq.size(); i++) {

            x = nodesQueue.poll();
            y = nodesQueue.poll();
            z = new DefaultMutableTreeNode("=" + freq.sumFreq(x, y));
            z.add(x);
            z.add(y);
            nodesQueue.add(z);

        }
        DefaultMutableTreeNode root2 = nodesQueue.poll();
        huffmanTree = root2;
        JTree tree = new JTree(root2);
        add(tree);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("JTree Example");
        this.pack();
        this.setVisible(true);
        Codes(root2, "");

        return map;

    }

    public void Codes(DefaultMutableTreeNode node, String code) {
        if (node.isLeaf()) {
            System.out.println((char) freq.extractAscii(node) + "=" + code);
            map.put(freq.extractAscii(node), code);
            return;

        }
        Codes((DefaultMutableTreeNode) node.getChildAt(0), code + "0");
        Codes((DefaultMutableTreeNode) node.getChildAt(1), code + "1");
    }

    FileWriter fw;
    BufferedWriter writer;

    public void writeHeader(DefaultMutableTreeNode node, String fileName) throws IOException {
        fw = new FileWriter(fileName);
        writer = new BufferedWriter(fw);
        postOrder(node);
        //uses post order traversal

        System.out.println("");
        writer.append("START");
        writer.close();
        fw.close();
    }

    public void postOrder(DefaultMutableTreeNode node) throws IOException {
        if (node.isLeaf()) {
            if (freq.extractAscii(node)==10)
            {
                writer.append("1");
                writer.newLine();
                writer.append("");

            }
            else
            {
                 writer.append("1" + (char) freq.extractAscii(node));
            }
            
        } else {
            writer.append(""+0);
            postOrder((DefaultMutableTreeNode) node.getChildAt(0));//left
            postOrder((DefaultMutableTreeNode) node.getChildAt(1));//right
            

        }
    }

}
