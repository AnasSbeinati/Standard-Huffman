package Huffman;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import javax.swing.JOptionPane;
public class HuffmanFunctions {
	static public Char[]fillChar(File file) {
		Char[] temp=new Char[127];
		int capacity=0;
		if(file.isFile())
		{
			FileReader fileReader;
			try {
				fileReader = new FileReader(file);
				BufferedReader buffer=new BufferedReader(fileReader);
				String str="";
				int lines=0;
				while((str=buffer.readLine())!=null)
				{
					lines++;
					for (int i = 0; i < str.length(); i++) {
						boolean isNew=true;
						for (int j = 0; j < capacity; j++) {
							if(str.charAt(i)==temp[j].getName()) {
								temp[j].setProbably();
								isNew=false;
								break;
							}
						}
						if(isNew)
						{
							Char tempChar=new Char(str.charAt(i));
							capacity++;
							temp[capacity-1]=tempChar;
						}
					}
				}
				if(lines!=0) {
					Char tempChar=new Char('\n');
					capacity++;
					temp[capacity-1]=tempChar;
				}
				for (int i = capacity; i < temp.length; i++) {
					temp[i]=null;
				}
					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return temp;
	}
    static public ArrayList<HuffmanTree>fillPriorityQueu(Char[] character)
    {
    	ArrayList<HuffmanTree> tempQueu =new ArrayList<HuffmanTree>();
    	for (Char char1 : character) {
    		if(char1!=null) {
    			HuffmanTree tempTree=new HuffmanTree();
    		    tempTree.setNode(char1);
			    tempQueu.add(tempTree);
    		}
    		else
    			break;
		}
    	tempQueu.sort(new Compare());
    	return tempQueu;
    }
    static public HuffmanTree huffman(ArrayList<HuffmanTree> queue) {
    	HuffmanTree tempTree_1=new HuffmanTree();
    	HuffmanTree tree=new HuffmanTree();
    	while(queue.size()!=1) {
    		tempTree_1=new HuffmanTree();
    		tree=new HuffmanTree();
    		int newPer=0;
    		//Extract the first tree
    		tempTree_1=queue.get(0);
    		queue.remove(0);
    		newPer=tempTree_1.getNode().getProbably();
    		tree.setlN(tempTree_1);
    		//Extract the second tree
    		tempTree_1=queue.get(0);
    		queue.remove(0);
    		newPer+=tempTree_1.getNode().getProbably();
    		tree.setrN(tempTree_1);
    		//put the new probablity
    		Char tempChar=new Char();
    		tempChar.setProbably(newPer);
    		tree.setNode(tempChar);
    		//put the new tree in queue
    		queue.add(tree);
    		queue.sort(new Compare());
    	}
    	return queue.get(0);
    }
    static public void setCode(HuffmanTree tree,String code) {
    	if(tree.lN!=null) {
    		tree.lN.setCode(code);
    		setCode(tree.lN,code+"0");
    	}
    	if(tree.rN!=null) {
    		if(code.length()>1)
    		   code=code.substring(0, code.length()-1)+"1";
    		else
    			code="1";
    		tree.rN.setCode(code);
    		setCode(tree.rN,code+"0");
    	}
    }
    static public void getCode(HuffmanTree tree,Hashtable<String, String> table) {
    	if((tree.lN==null)&&(tree.rN==null)) {
    		String temp="";
    		temp+=tree.getNode().getName();
    		table.put(temp, tree.getNode().getCode());
    	}
    	if(tree.lN!=null) {
    		getCode(tree.lN,table);
    	}
    	if(tree.rN!=null) {
    		getCode(tree.rN,table);
    	}
    }
    static public Hashtable<String, String> getTable(File file) {
    	 Hashtable<String, String> table=new  Hashtable<String, String>();
    	 Char[] characters=fillChar(file);
    	 ArrayList<HuffmanTree> queue=fillPriorityQueu(characters);
    	 HuffmanTree tree=huffman(queue);
    	 setCode(tree,"0");
    	 getCode(tree,table);
    	 return table;
    }
    static public int compress(File file,File file2) {
    	int num=0;
    	if(file.isFile())
		{
    		Hashtable<String, String> table=getTable(file);
    		//HashMap<String, String>table1=getMap(table);
    		try
    		{
    			FileReader inFile=new FileReader(file);
        	    BufferedReader buffer=new BufferedReader(inFile);
        	    long fileSize=file.length();
        	    long size=fileSize%1000;
        	    if(fileSize<1000)
        	    {
        	    	char[] text1=new char[(int)size];
        	    	buffer.read(text1,0,(int)size);
        	    	String eee=String.copyValueOf(text1);
        	    	String temp=getStrCompressed(eee,table);
    		    	//write in bits
        	    	FileOutputStream fos = new FileOutputStream(file2);
        	    	ObjectOutputStream oos = new ObjectOutputStream(fos);
        	    	int i=0,size1=temp.length();
        	    	Set<String> keys = table.keySet();
        	    	oos.writeInt(keys.size());
        	        for(String key: keys){
        	    		String value=table.get(key);
        	    		oos.writeObject(value);
        	    		oos.writeObject(key);
        	    		System.out.println(value+" "+key);
        	        }
        	    	//oos.writeObject(table1);
        	    	while(i<size1) {
        	    		if(temp.length()<31) {
        	    			int strSize=temp.length();
        	    			String dd="";
        	    			for (int j = strSize; j < 31; j++) {
								dd+="0";
							}
        	    			dd+=temp;
        	    			int rr=Integer.parseInt(dd,2);
        	    			oos.writeInt(rr);
        	    			num++;
        	    			oos.writeInt(strSize);
        	    			num++;
        	    			//System.out.print(Integer.toBinaryString(rr));
        	    			break;
        	    		}
        	    		String tempStr=temp.substring(0, 31);
        	    		temp=temp.substring(31, temp.length());
        	    		i+=31;
        	    		int rr=Integer.parseInt(tempStr,2);
        	    		oos.writeInt(rr);
        	    		num++;
        	    		//System.out.print(Integer.toBinaryString(rr));
        	    	}
        	    	 oos.close();
        	    	 fos.close();
        	    }
        	    else
        	    {
        	    	long index= file.length();
        	        char[] text=new char[1000];
        	        FileOutputStream fos = new FileOutputStream(file2);
        	    	ObjectOutputStream oos = new ObjectOutputStream(fos);
    		        while(buffer.read(text,0, 1000)!=-1)
    		        {
    		        	 String temp=getStrCompressed(String.copyValueOf(text),table);
    		        	 //write in bits
    		        	 oos.writeInt(Integer.parseInt(temp,2));
    		    	     index-=1000;
    		    	     if(index<1000)
    		     	     {
    		     	    	char[] text1=new char[(int)index];
    		     	    	buffer.read(text1,0,(int)index);
    		     	    	 String temp1=getStrCompressed(String.copyValueOf(text1),table);
        		        	 //write in bits
    		     	    	oos.writeInt(Integer.parseInt(temp1,2));
    		     	     }
    		        }
    		        oos.close();
       	    	    fos.close();
        	    }
    		    buffer.close();
    		}
    		catch(Exception e)
    		{
    			JOptionPane.showMessageDialog(null,"Something wrong");
    		}
		}
    	 return num;
    }
    static public String getStrCompressed(String text,Hashtable<String, String> table)
    {
    	String strComp="";
    	for (int i = 0; i < text.length(); i++) {
    		String str="";
    		if(text.charAt(i)!='\r') {
    		   str+=text.charAt(i);
			   strComp+=table.get(str);
    		}
		}
    	return strComp;
    }
    static public void decompress(File file,File file2,int num) {
    	Hashtable<String, String>table=new Hashtable<String, String>();
    	String bitStream=getBitStream(file,table,num);
    	/*Set<String> keys = table.keySet();
        for(String key: keys){
    		String value=table.get(key);
    		reverseTable.put(value, key);
    	}*/
    	String stream=getStream(bitStream,table);
    	System.out.print(stream);
    }
    static public String getBitStream(File file,Hashtable<String, String> table,int num) {
    	FileInputStream reader;
    	ObjectInputStream buffer;
    	String stream="";
		try {
			//HashMap<String, String>table1=new HashMap<>();
			reader = new FileInputStream(file);
			buffer=new ObjectInputStream(reader);
			//table1=(HashMap<String, String>)t;
			long r=file.length();
			int Size=buffer.readInt();
			for (int i = 0; i < Size; i++) {
				Object obj1=buffer.readObject();
				Object obj2=buffer.readObject();
				String key=(String)obj1;
				String value=(String)obj2;
				table.put(key, value);
			}
			//table=getHash(table);
			int current=0;
			int rrrr=reader.available();
			while(num>0) {
				int tempInt=buffer.readInt();
				num--;
				int rrr=reader.available();
				if(num==1) {
					String tempStr1=Integer.toBinaryString(tempInt);
					int size=buffer.readInt();
					if(tempStr1.length()<31) {
						String appe="";
						for (int i =tempStr1.length() ; i < size; i++) {
							appe+="0";
						}
						tempStr1=appe+tempStr1;
						stream+=tempStr1;
						break;
					}
				}
				String tempStr=Integer.toBinaryString(tempInt);
				if(tempStr.length()<31) {
					String appe="";
					for (int i =tempStr.length() ; i < 31; i++) {
						appe+="0";
					}
					tempStr=appe+tempStr;
				}
				stream+=tempStr;
			}
			buffer.close();
	    	reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return stream;
    }
    static public String getStream(String bitStream,Hashtable<String, String> table) {
    	String stream="";
    	int i=0;
    	String str="";
    	while(i<bitStream.length()) {
    		str+=bitStream.charAt(i);
    		i++;
    		String decoded="";
    		if((decoded=table.get(str))!=null) {
    			str="";
    			stream+=decoded;
    		}
    	}
    	return stream;
    }
    static public HashMap<String, String> getMap(Hashtable<String, String>table) {
    	HashMap<String, String> table1 = new HashMap<String,String>();
    	Set<String> keys = table.keySet();
        for(String key: keys){
    		String value=table.get(key);
    		table1.put(value, key);
    		}
        return table1;
        }
    static public Hashtable<String, String> getHash(HashMap<String, String>table1) {
    	Hashtable<String, String> table = new Hashtable<String,String>();
    	Set<String> keys = table1.keySet();
        for(String key: keys){
    		String value=table.get(key);
    		table1.put(key,value);
    		}
        return table;
        }
}