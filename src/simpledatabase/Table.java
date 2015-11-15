package simpledatabase;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Table extends Operator{
	private BufferedReader br = null;
	private boolean getAttribute=false;
	private Tuple tuple;

	
	public Table(String from){
		this.from = from;
		
		//Create buffer reader
		try{
			br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/datafile/"+from+".csv")));
		}
		catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	
	/**
     * Create a new tuple and return the tuple to its parent.
     * Set the attribute list if you have not prepare the attribute list
     * @return the tuple
     */
	@Override
	public Tuple next(){
		String comma = ",";
		String temp;
		try {
			if((temp = br.readLine())!= null){
				if(getAttribute){
					tuple.col2 = temp.split(comma);
					tuple.setAttributeValue();
					return tuple;
				}else{
					String temp2 = br.readLine();
					String temp3 = br.readLine();
					tuple = new Tuple(temp,temp2,temp3);
					tuple.setAttributeName();
					tuple.setAttributeType();
					tuple.setAttributeValue();
					getAttribute = true;
					return tuple;
				}
			}else{
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	

	/**
     * The function is used to get the attribute list of the tuple
     * @return the attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return tuple.getAttributeList();
	}
	
}