
public class Potion extends SellableObject{
	private double agilityBoost;
	private double strengthBoost;
	private double dexerityBoost;
	private double hpBoost;
	private double manaBoost;
	
	public Potion() {
		super();
		setAgilityBoost(0);
		setStrengthBoost(0);
		setDexerityBoost(0);
		setHPBoost(0);
		setManaBoost(0);
	}
	public Potion(String name, double price,int minlevelreq, double ab, double sb, double db, double hb, double mb) {
		this();
		this.setName(name);
		this.setPrice(price);
		this.setLevelReq(minlevelreq);
	}
	
	
	public void setAgilityBoost(double a) {
		if(a < 0) {
			throw new IllegalArgumentException();
		}
		agilityBoost = a;
	}
	public void setStrengthBoost(double s) {
		if(s < 0) {
			throw new IllegalArgumentException();
		}
		strengthBoost = s;
	}
	public void setDexerityBoost(double d) {
		if(d < 0) {
			throw new IllegalArgumentException();
		}
		dexerityBoost = d;
	}
	public void setHPBoost(double h) {
		if(h < 0) {
			throw new IllegalArgumentException();
		}
		hpBoost = h;
	}
	public void setManaBoost(double m) {
		if(m <0) {
			throw new IllegalArgumentException();
		}
		manaBoost = m;
	}
	public double getAgilityBoost() {
		return agilityBoost;
	}
	public double getStrengthBoost() {
		return strengthBoost;
	}
	public double getDexerityBoost() {
		return dexerityBoost;
	}
	public double getHPBoost() {
		return hpBoost;
	}
	public double getManaBoost() {
		return manaBoost;
	}
	public void applyPotion(Hero h) {
		h.increaseAgility(agilityBoost);
		h.increaseDexerity(dexerityBoost);
		h.increaseStrength(strengthBoost);
		h.increaseHP(hpBoost);
		h.increaseMana(manaBoost);
	}
	public String toString() {
		String ret =  "Potion Name: "+this.getName() +"\n";
		ret += "     Selling Price: "+ this.getPrice()+'\n';
		ret += "     BuyBack Price: "+ this.getPrice()/2+'\n';
		ret += "     Minimum Hero Level Requirement: "+ this.getLevelReq()+'\n';
		if(agilityBoost > 0){
			ret += "     Agility Boost: "+ agilityBoost+'\n';
		}
		if(strengthBoost > 0){
			ret += "     Strength Boost: "+ strengthBoost+'\n';
		}
		if(dexerityBoost > 0){
			ret += "     Dexerity Boost: "+ dexerityBoost+'\n';
		}
		if(manaBoost > 0){
			ret += "     Mana Boost: "+ manaBoost+'\n';
		}
		if(hpBoost > 0){
			ret += "     HP Boost: "+ hpBoost+'\n';
		}
		return ret;
	}
}
