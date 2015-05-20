package Huffman;
import java.util.Comparator;
public class HuffmanTree implements Comparable<HuffmanTree>{
	private Char node;
	HuffmanTree lN;
	HuffmanTree rN;
	public HuffmanTree() {
		this.node = null;
		this.lN = null;
		this.rN = null;
	}
	public Char getNode() {
		return node;
	}
	public void setNode(Char node) {
		this.node = node;
	}
	public HuffmanTree getlN() {
		return lN;
	}
	public void setlN(HuffmanTree lN) {
		this.lN = lN;
	}
	public HuffmanTree getrN() {
		return rN;
	}
	public void setrN(HuffmanTree rN) {
		this.rN = rN;
	}
	@Override
	public int compareTo(HuffmanTree other) {
		// TODO Auto-generated method stub
		if(getNode().getProbably()>other.getNode().getProbably())
			return -1;
		if(getNode().getProbably()<other.getNode().getProbably())
			return 1;
		return 0;
		//return Integer.valueOf(getNode().getProbably()).compareTo(other.getNode().getProbably());
	}
    public void setCode(String code) {
    	this.node.setCode(code);
    }
}
