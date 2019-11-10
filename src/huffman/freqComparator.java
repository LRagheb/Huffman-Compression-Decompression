/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

import java.util.Comparator;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Loujaina
 */
public class freqComparator implements Comparator<DefaultMutableTreeNode> {

    final String regex = "(\\d+)?\\=(\\d+)";
    final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
    Matcher matcher;
    int f1, f2, ascii;
    char c;
String nodeString;
    @Override
    public int compare(DefaultMutableTreeNode one, DefaultMutableTreeNode two) {

        f1 = extractFreq(one);
        f2 = extractFreq(two);

        return f1-f2;
    }

    public int extractFreq(DefaultMutableTreeNode node) {
        nodeString=node.toString().trim();
        matcher = pattern.matcher(nodeString);
        int freq = 0;
        if (matcher.matches()) {
            freq = Integer.parseInt(matcher.group(2));
        } else {
            System.out.println("ERROR!" + node.toString());
        }
        return freq;

    }

    public int extractAscii(DefaultMutableTreeNode node) {
        //only send leaf nodes
        matcher = pattern.matcher(node.toString());
        if (matcher.matches()) {
            ascii= Integer.parseInt(matcher.group(1));
        }
        return ascii;
    }

    public int sumFreq(DefaultMutableTreeNode one, DefaultMutableTreeNode two) {
        f1 = extractFreq(one);
        f2 = extractFreq(two);

        return f1 + f2;
    }
    
}
