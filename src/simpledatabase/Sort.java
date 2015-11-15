package simpledatabase;
import java.util.ArrayList;

public class Sort extends Operator{
	
	private ArrayList<Attribute> newAttributeList;
	private String orderPredicate;
	ArrayList<Tuple> tuplesResult;

	
	public Sort(Operator child, String orderPredicate){
		this.child = child;
		this.orderPredicate = orderPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuplesResult = new ArrayList<Tuple>();
		
	}
	
	
	/**
     * The function is used to return the sorted tuple
     * @return tuple
     */
	@Override
	public Tuple next(){
		Tuple cur;
		int aIndex=0;
		while((cur = child.next())!=null){
			aIndex = 0;
			ArrayList<Attribute> temp = new ArrayList<Attribute>();
			for(Attribute curA:cur.getAttributeList()){
				Attribute tempA = new Attribute();
				tempA.attributeName = curA.getAttributeName();
				tempA.attributeType = curA.getAttributeType();
				tempA.attributeValue = curA.getAttributeValue();
				temp.add(tempA);
			}
			for(Attribute curA:cur.getAttributeList()) 
				if(curA.getAttributeName().equals(orderPredicate)) break;
				else aIndex++;
			tuplesResult.add(new Tuple(temp));
			//sort tuple
			ArrayList<Tuple> tempList = new ArrayList<Tuple>();
			tempList.add(tuplesResult.remove(0));
			for(Tuple curT:tuplesResult){
				int i = 0;
				for(Tuple tempCur:tempList){
					if(curT.getAttributeValue(aIndex).toString().compareTo(tempCur.getAttributeValue(aIndex).toString())>0){
						i++;
					}
				}
				tempList.add(i,curT);
			}
			tuplesResult = tempList;
		}
		return tuplesResult.isEmpty()?null:tuplesResult.remove(0);
		
	}
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return child.getAttributeList();
	}

}