
public class Weapon extends SellableObject {
	private double baseDamage;
	private int numOfHandsReq;
	
	//Constructors
	public Weapon() {
		super();
	}
	public Weapon(String name, double price,int minlevelreq, double bd, int hands) {
		this();
		this.setName(name);
		this.setPrice(price);
		this.setLevelReq(minlevelreq);
		setDamage(bd);
		setNumOfHands(hands);
	}
	
	// Accessor Methods
	public double getDamage() {
		return baseDamage;
	}

	public int getNumOfHands() {
		return numOfHandsReq;
	}
	// Setter Methods
	public void setDamage(double d) {
		if(d < 0) {
			throw new IllegalArgumentException();
		}
		baseDamage = d;
	}
	
	public void setNumOfHands(int i) {
		if(i < 0 || i > 2) {
			throw new IllegalArgumentException();
		}
		numOfHandsReq = i;
	}
	// Modifier Methods 
	
	public String toString() {
		String ret =  "Weapon Name: "+this.getName() +"\n";
		ret += "     Selling Price: "+ this.getPrice()+'\n';
		ret += "     BuyBack Price: "+ this.getPrice()/2+'\n';
		ret += "     Minimum Hero Level Requirement: "+ this.getLevelReq()+'\n';
		ret += "     Base Damage: "+ this.getDamage()+'\n';
		ret += "     Number of Hands: "+ numOfHandsReq+'\n';
		return ret;
	}
	
	
}
