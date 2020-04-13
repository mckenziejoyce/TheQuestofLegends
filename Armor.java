
public class Armor extends SellableObject{
	private double protection;
	
	public Armor() {
		super();
		setProtection(0);
	}
	public Armor(String name, double price,int minlevelreq, double protec) {
		this();
		this.setName(name);
		this.setPrice(price);
		this.setLevelReq(minlevelreq);
		setProtection(protec);
	}
	
	public void setProtection(double d) {
		if(d<0) {
			throw new IllegalArgumentException();
		}
		protection = d;
	}
	public double getProtection() {
		return protection;
	}
	public String toString() {
		String ret =  "Armor Name: "+this.getName() +"\n";
		ret += "     Selling Price: "+ this.getPrice()+'\n';
		ret += "     BuyBack Price: "+ this.getPrice()/2+'\n';
		ret += "     Minimum Hero Level Requirement: "+ this.getLevelReq()+'\n';
		ret += "     Protection: "+ protection + '\n';
		return ret;
	}
}
