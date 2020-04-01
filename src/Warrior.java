
public class Warrior extends Hero {
	double defaultAgility = 20;
	double defaultStrength = 20;
	double defaultDexerity = 10;
	
	public Warrior() {
		super();
		setSkills(defaultAgility, defaultStrength, defaultDexerity);
	}
	
	public Warrior(double a, double s, double d) {
		this();
		setSkills(a, s, d);
	}
	public Warrior(String name, double level, double xp, double mana, double a, double s, double d) {
		this();
		this.setName(name);
		this.setLevel(level);
		this.increaseCoins(level*50);
		this.setXP(xp);
		this.setMana(mana);
		setSkills(a, s, d);
		Weapon w = new Weapon("Basic_Weapon",level*10,(int)level,level*10,1);
		Armor arm = new Armor("Basic_Armor",level*10,(int)level,(level*10)/3);
		Potion p = new Potion("Basic_Potion",level*10,(int)level,(this.getAgility()*.03),(this.getStrength()*.03),this.getDexerity()*.03,10,10);
		Spell spell = new Spell("Basic_Spell",level*10,(int)level,"Lightning",10,this.getMana()*.2,level*15);
		this.addArmor(arm);
		this.addWeapon(w);
		this.addSpell(spell);
		this.addPotion(p);
		this.setCurWeapon(w);
		this.setCurArmor(arm);
	}
	


	public void setSkills(double a, double s, double d) {
		if(d >= a || d >= s) {
			throw new IllegalArgumentException();
		}
		this.setAgility(a);
		this.setDexerity(d);
		this.setStrength(s);
		
	}


	public void levelUp() {
		this.increaseSkillsPercent(5);
		this.increaseAgilityPercent(5);
		this.increaseStrengthPercent(5);
		this.setHP(100*this.getLevel());
		double curmana = this.getMana();
		this.setMana(curmana+curmana*.1);
		
		
	}
	public String toString() {
		String ret = "This warrior's name is "+this.getName();
		ret += "\n Level: "+ this.getLevel();
		ret += ", HP: "+this.getHP() + ", Mana: "+ this.getMana();
		ret += ", XP: "+ this.getXP() + ", Money: "+this.getMoney();
		ret += "\n Strength: "+ this.getStrength()+ " ,Agility: "+this.getAgility()+ ", Dexerity: "+this.getDexerity();
		if(this.isFighting()) {
			ret += this.getCurArmor();
			ret += this.getCurWeapon();
		}
		return ret;
	}
	
	
}
