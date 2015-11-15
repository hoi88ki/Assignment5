package simpledatabase;
import java.util.ArrayList;

public class Projection extends Operator{
	
	ArrayList<Attribute> newAttributeList;
	private String attributePredicate;


	public Projection(Operator child, String attributePredicate){
		this.attributePredicate = attributePredicate;
		this.child = child;
		newAttributeList = new ArrayList<Attribute>();
		
	}
	
	
	/**
     * Return the data of the selected attribute as tuple format
     * @return tuple
     */
	@Override
	public Tuple next(){
		Tuple cur;
		Tuple result = null;
		if((cur = child.next()) != null)
			for(Attribute curA:cur.getAttributeList()){
				if(attributePredicate.equals(curA.getAttributeName())){
					ArrayList<Attribute> temp = new ArrayList<Attribute>();
					Attribute tempA = new Attribute();
					tempA.attributeName = curA.getAttributeName();
					tempA.attributeType = curA.getAttributeType();
					tempA.attributeValue = curA.getAttributeValue();
					temp.add(tempA);
					result = new Tuple(temp);
				}
			}
		return result;
	}
		

	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return child.getAttributeList();
	}

}