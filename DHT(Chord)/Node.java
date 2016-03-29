import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;



public class Node implements Serializable {
	String NodeKey;
	String NodeValue;
	boolean IsANode;
	Node Succesor;
	Node Predecessor;
	int NodePort;
	int NodeHash;
	Map<String, String> Container;
	Vector<String> VectorofValues;
	Vector<String> VectorofKeys;	
	Vector<Integer> VectorofHashes;
	
	Node (String n)
	{
		NodeKey=n;
		NodeValue=new String();
		Succesor=null;
		Predecessor=null;
		Container=new HashMap<String,String>();
		IsANode=true;
		VectorofValues= new Vector<String>();
		VectorofKeys= new Vector<String>();
		VectorofHashes = new Vector <Integer>();
	}
	
	void ComputerHash(String n,int a)
	{
		NodeHash=(n.hashCode()%a);
		if (NodeHash<0)
			NodeHash=-NodeHash;
	}
	
	

}
