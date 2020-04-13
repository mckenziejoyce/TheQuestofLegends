
public class Spell extends SellableObject{
	private double damageRange;
	private double manaNeeded;
	private double baseDamage;
	private String spellType;
	private final String fire = "Fire";
	private final String ice = "Ice";
	private final String lightning = "Lightning";
	
	public Spell() {
		super();
		setDamageRange(0);
		setManaNeeded(0);
		setBaseDamage(0);
		setSpellType(fire);
		
	}
	public Spell(String name, double price,int minlevelreq,String spellType, double dr, double mana, double bd) {
		this();
		this.setName(name);
		this.setPrice(price);
		this.setLevelReq(minlevelreq);
		setDamageRange(dr);
		setManaNeeded(mana);
		setBaseDamage(bd);
		setSpellType(spellType);
	}
	public void setDamageRange(double d) {
		if(d < 0) {
			throw new IllegalArgumentException();
		}
		damageRange = d;
	}
	public void setManaNeeded(double m) {
		if(m < 0) {
			throw new IllegalArgumentException();
		}
		manaNeeded = m;
	}
	public void setBaseDamage(double d) {
		if(d < 0) {
			throw new IllegalArgumentException();
		}
		baseDamage = d;
	}
	public void setSpellType(String spellType) {
		if(spellType.compareTo(fire)==0) {
			this.spellType = fire;
		}
		else if(spellType.compareTo(ice)==0) {
			this.spellType = ice;
		}
		else if(spellType.compareTo(lightning)==0) {
			this.spellType = lightning;
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	public String getSpellType() {
		return spellType;
	}
	public double getDamageRange() {
		return damageRange;
	}
	public double getManaNeeded() {
		return manaNeeded;
	}
	public double getBaseDamage() {
		return baseDamage;
	}
	public String toString() {
		String ret =  "Spell Name: "+this.getName() +"\n";
		ret += "     Selling Price: "+ this.getPrice()+'\n';
		ret += "     BuyBack Price: "+ this.getPrice()/2+'\n';
		ret += "     Minimum Hero Level Requirement: "+ this.getLevelReq()+'\n';
		ret += "     Amount of Mana Used: "+ manaNeeded+'\n';
		ret += "     Damage Range: "+ this.getDamageRange()+'\n';
		ret += "     Base Damage: "+ this.getBaseDamage()+'\n';
		return ret;
	}
}
