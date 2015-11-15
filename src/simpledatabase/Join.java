package simpledatabase;
import java.util.ArrayList;

public class Join extends Operator{

	private ArrayList<Attribute> newAttributeList;
	private String joinPredicate;
	ArrayList<Tuple> tuples1;

	
	//Join Constructor, join fill
	public Join(Operator leftChild, Operator rightChild, String joinPredicate){
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.joinPredicate = joinPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuples1 = new ArrayList<Tuple>();
		
	}

	
	/**
     * It is used to return a new tuple which is already joined by the common attribute
     * @return the new joined tuple
     */
	//The record after join with two tables
	@Override
	public Tuple next(){
		Tuple leftCur, rightCur;
		int lCounter = -1, rCounter=-2;
		while((leftCur = leftChild.next())!=null){
			ArrayList<Attribute> temp = new ArrayList<Attribute>();
			for(Attribute curA:leftCur.getAttributeList()){
				Attribute tempA = new Attribute();
				tempA.attributeName = curA.getAttributeName();
				tempA.attributeType = curA.getAttributeType();
				tempA.attributeValue = curA.getAttributeValue();
				temp.add(tempA);
			}
			tuples1.add(new Tuple(temp));
			
		}
		if((rightCur = rightChild.next())== null) return null;
		
		for(Attribute curR:rightCur.getAttributeList()){
			lCounter++;
			rCounter = -2;
			for(Attribute curL:tuples1.get(0).getAttributeList()){
				if(curL.getAttributeName().equals(curR.getAttributeName())){
					break;
				}
				rCounter++;
			}
			break;
		}
		for(Tuple curT: tuples1){
			if(curT.getAttributeName(lCounter).equals(rightCur.getAttributeName(rCounter))&&curT.getAttributeValue(lCounter).equals(rightCur.getAttributeValue(rCounter))){
				ArrayList<Attribute> temp = new ArrayList<Attribute>();
				for(Attribute curA:curT.getAttributeList()){
					Attribute tempA = new Attribute();
					tempA.attributeName = curA.getAttributeName();
					tempA.attributeType = curA.getAttributeType();
					tempA.attributeValue = curA.getAttributeValue();
					temp.add(tempA);
				}
				for(Attribute curA:rightCur.getAttributeList()){
					Attribute tempA = new Attribute();
					tempA.attributeName = curA.getAttributeName();
					tempA.attributeType = curA.getAttributeType();
					tempA.attributeValue = curA.getAttributeValue();
					if(!tempA.getAttributeName().equals(rightCur.getAttributeName(rCounter))) temp.add(tempA);
				}
				tuples1.remove(curT);
				return new Tuple(temp);
			}
		}
		return null;
	}
	
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		if(joinPredicate.isEmpty())
			return child.getAttributeList();
		else
			return(newAttributeList);
	}

}