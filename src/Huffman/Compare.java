package Huffman;

import java.util.Comparator;

public class Compare implements Comparator<HuffmanTree>{

	@Override
	public int compare(HuffmanTree t1, HuffmanTree t2) {
		if(t1.getNode().getProbably()>t2.getNode().getProbably())
			return 1;
		if(t1.getNode().getProbably()<t2.getNode().getProbably())
			return -1;
		return 0;
	}
	
}
