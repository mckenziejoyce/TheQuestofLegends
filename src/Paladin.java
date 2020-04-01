
public class Paladin extends Hero{
	double defaultAgility = 10;
	double defaultStrength = 20;
	double defaultDexerity = 20;
	
	public Paladin() {
		super();
		setSkills(defaultAgility, defaultStrength, defaultDexerity);
	}
	
	
	public Paladin(double d, double a, double s) {
		this();
		setSkills(d, a, s);
	}
	public Paladin(String name, double level, double xp, double mana, double a, double s, double d) {
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
		Spell spell = new Spell("Basic_Spell",level*10,(int)level,"Fire",10,this.getMana()*.2,level*15);
		this.addArmor(arm);
		this.addWeapon(w);
		this.addSpell(spell);
		this.addPotion(p);
		this.setCurWeapon(w);
		this.setCurArmor(arm);
	}
	
	public void setSkills(double a, double s, double d) {
		if(a >= s || a >= d) {
			throw new IllegalArgumentException();
		}
		this.setAgility(a);
		this.setDexerity(d);
		this.setStrength(s);
		
	}
	
	
	public void levelUp() {
		this.increaseSkillsPercent(5);
		this.increaseDexerityPercent(5);
		this.increaseStrengthPercent(5);
		
	}
	public String toString() {
		String ret = "This paladin's name is "+this.getName();
		ret += "\n Level: "+ this.getLevel();
		ret += ", HP: "+this.getHP() + ", Mana: "+ this.getMana();
		ret += ", XP: "+ this.getXP() + ", Money: "+this.getMoney();
		ret += "\n Strength: "+ this.getStrength()+ " ,Agility: "+this.getAgility()+ ", Dexerity: "+this.getDexerity();
		return ret;
	}
}
