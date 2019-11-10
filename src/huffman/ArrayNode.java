package huffman;

public class ArrayNode /*implements Comparable<ArrayNode>*/{
	int ascii;
	int freq;
	
	
	public int getAscii() {
		return ascii;
	}
	public void setAscii(int ascii) {
		this.ascii = ascii;
	}
	public int getFreq() {
		return freq;
	}
	public void setFreq(int freq) {
		this.freq = freq;
	}
	
	
	public ArrayNode() {
		super();
	}
	
	public ArrayNode(int ascii) {
		super();
		this.ascii = ascii;
	}
	
	public ArrayNode(int ascii, int freq) {
		super();
		this.ascii = ascii;
		this.freq = freq;
	}
	
//	@Override
//	public int compareTo(ArrayNode arg) {
//		
//		return getFreq().compareTo(arg.getFreq());
//	}
	
	
	
	
}
