package Huffman;

public class Char {
	private char name;
	private int probably;
	private String code;
	public Char() {
	}
	public Char(char name) {
		super();
		this.name = name;
		this.probably = 1;
		this.code = "";
	}
	public int getProbably() {
		return probably;
	}
	public void setProbably(int pro) {
		this.probably=pro;
	}
	public void setProbably() {
		this.probably++;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public char getName() {
		return name;
	}
	public void setName(char name) {
		this.name = name;
	}
	

}
