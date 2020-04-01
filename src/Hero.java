
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Hero {
	private int levelCap = 1000000000;
	private int hpCap = 1000000000;
	private int manaCap = 1000000000;
	private int skillsCap = 1000000000;
	private double winReward = 150;
	
	
	private String name;
	private double level;
	private double xp;
	private double healthPower;
	private double mana;
	private List<Weapon> weapons;
	private Weapon curWeapon;
	private List<Armor> armors;
	private Armor curArmor;
	private List<Potion> potions;
	private List<Spell> spells;
	private double agility;
	private double strength;
	private double dexerity;
	private double dodgeProbability;
	private Wallet wallet;
	private boolean alive;
	private boolean inFight;
	private Market market;
	
	//Constructors
	public Hero() {
		name = "";
		alive = true;
		inFight = false;
		setLevel(0);
		setHP(0);
		setMana(0);
		setAgility(0);
		setDexerity(0);
		setStrength(0);
		dodgeProbability = calculateDodgeProbability();
		wallet = new Wallet(level*50);
		market = new Market(this);
		potions = new ArrayList<Potion>();
		spells = new ArrayList<Spell>();
		armors = new ArrayList<Armor>();
		weapons = new ArrayList<Weapon>();
	}
	public Hero(double l, double hp, double mn) {
		this();
		setLevel(l);
		wallet.increaseCoins(level*50);
		setHP(hp);
		setMana(mn);
	}
	public Hero(double l, double hp, double mn, double a, double s, double d) {
		this();
		setLevel(l);
		wallet.increaseCoins(level*50);
		setHP(hp);
		setMana(mn);
		setAgility(a);
		setStrength(s);
		setDexerity(d);
	}
	
	//Accessor methods 
	public String getName() {
		return name;
	}
	public double getLevel() {
		return level;
	}
	public double getXP() {
		return xp;
	}
	public double getHP() {
		return healthPower;
	}
	public double getMana() {
		return mana;
	}
	public List<Weapon> getWeapons() {
		return weapons;
	}
	public List<Armor> getArmors() {
		return armors;
	}
	public List<Potion> getPotions() {
		return potions;
	}
	public List<Spell> getSpells() {
		return spells;
	}
	public double getAgility() {
		return agility;
	}
	public double getStrength() {
		return strength;
	}
	public double getDexerity() {
		return dexerity;
	}
	public double getDodgeProbability() {
		return dodgeProbability;
	}
	public Wallet getWallet() {
		return wallet;
	}
	public double getMoney() {
		return wallet.getCoins();
	}
	public Market getMarket() {
		return market;
	}
	public boolean isAlive() {
		return alive;
	}
	public boolean isFighting() {
		return inFight;
	}
	public Weapon getCurWeapon() {
		return curWeapon;
	}
	public Armor getCurArmor() {
		return curArmor;
	}

	//Setter methods 
	public void setName(String name) {
		this.name = name;
	}
	public void setLevel(double n) {
		if(n < 0 || n > levelCap) {
			throw new IllegalArgumentException();
		}
		level = n;
		setHP(n*100);
	}
	public void setXP(double n) {
		if(n < 0) {
			throw new IllegalArgumentException();
		}
		if(n >= (level*10)) {
			levelUp();
		}
		xp = n;
	}
	public void setHP(double n) {
		if(n < 0 || n > hpCap) {
			throw new IllegalArgumentException();
		}
		healthPower = n;
	}
	public void setMana(double n) {
		if(n < 0|| n > manaCap) {
			throw new IllegalArgumentException();
		}
		mana = n;
	}
	public void setAgility(double n) {
		if(n < 0|| n > skillsCap) {
			throw new IllegalArgumentException();
		}
		agility = n;
		dodgeProbability = calculateDodgeProbability();
	}
	public void setDexerity(double n) {
		if(n < 0 || n > skillsCap) {
			throw new IllegalArgumentException();
		}
		dexerity = n;
	}
	public void setStrength(double n) {
		if(n < 0|| n > skillsCap) {
			throw new IllegalArgumentException();
		}
		strength = n;
	}
	private double calculateDodgeProbability() {
		return agility*0.02;
	}
	public void setMoney(double n) {
		if(n < 0) {
			throw new IllegalArgumentException();
		}
		wallet.setCoins(n);
	}
	
	
	//Modifier Methods 
	public void addWeapon(Weapon w) {
		weapons.add(w);
		if(weapons.size() == 1) {
			curWeapon = w;
		}
		this.getMarket().setSellableItems(this);
	}
	public void addSpell(Spell s) {
		spells.add(s);
		this.getMarket().setSellableItems(this);
	}
	public void addArmor(Armor a) {
		armors.add(a);
		if(armors.size() == 1) {
			curArmor = a;
		}
		this.getMarket().setSellableItems(this);
	}
	public void addPotion(Potion p) {
		potions.add(p);
		this.getMarket().setSellableItems(this);
	}
	public void addMoney(double n) {
		wallet.increaseCoins(n);
	}
	public void removeWeapon(Weapon w) {
		weapons.remove(w);
		this.getMarket().setSellableItems(this);
	}
	public void removeSpell(Spell s) {
		spells.remove(s);
		this.getMarket().setSellableItems(this);
	}
	public void removeArmor(Armor a) {
		armors.remove(a);
		this.getMarket().setSellableItems(this);
	}
	public void removePotion(Potion p) {
		potions.remove(p);
		this.getMarket().setSellableItems(this);
	}
	public void removeMoney(double n) {
		wallet.decreaseCoins(n);
	}
	// Increase skills by a certain number 
	public void increaseSkills(int n) {
		setAgility(agility+n);
		setStrength(strength+n);
		setDexerity(dexerity+n);
	}
	public void increaseAgility(double d) {
		setAgility(agility+d);
	}
	public void increaseStrength(double n) {
		setStrength(strength+n);
	}
	public void increaseDexerity(double n) {
		setDexerity(dexerity+n);
	}
	// Increase skills by percentage 
	public void increaseSkillsPercent(double n) {
		increaseAgilityPercent(n);
		increaseStrengthPercent(n);
		increaseDexerityPercent(n);
	}
	public void increaseAgilityPercent(double n) {
		double add = agility * (n/100);
		setAgility(agility+add);
	}
	public void increaseStrengthPercent(double n) {
		double add = strength * (n/100);
		setStrength(strength+add);
	}
	public void increaseDexerityPercent(double n) {
		double add = dexerity * (n/100);
		setDexerity(dexerity+add);
	}
	public void increaseXP(double x) {
		setXP(xp+x);
	}
	public void increaseHP(double x) {
		setHP(healthPower+x);
	}
	public void increaseMana(double m) {
		setMana(mana +m);
	}
	public void decreaseHP(double x) {
		if(healthPower-x <= 0) {
			setHP(0);
			alive = false;
		}
		else {
			setHP(healthPower-x);
		}
	}
	public void decreaseMana(double d) {
		setMana(mana-d);
	}
	public void getAttacked(double d) {
		Random rand = new Random();
		int i = rand.nextInt(101);
		if(i <= dodgeProbability) {
			return;
		}
		if(d-this.getCurArmor().getProtection() <0) {
			return;
		}
		decreaseHP(d-this.getCurArmor().getProtection());
	}
	public void buyItem(SellableObject o) {
		Weapon w = new Weapon();
		Armor a = new Armor();
		Spell s = new Spell();
		Potion p = new Potion();
		if(o.getClass() == w.getClass()) {
			this.addWeapon((Weapon)o);
		}
		if(o.getClass() == a.getClass()) {
			this.addArmor((Armor)o);
		}
		if(o.getClass() == s.getClass()) {
			this.addSpell((Spell)o);
		}
		if(o.getClass() == p.getClass()) {
			this.addPotion((Potion)o);
		}
	}
	public void sellItem(SellableObject o) {
		Weapon w = new Weapon();
		Armor a = new Armor();
		Spell s = new Spell();
		Potion p = new Potion();
		if(o.getClass() == w.getClass()) {
			this.removeWeapon((Weapon)o);
		}
		if(o.getClass() == a.getClass()) {
			this.removeArmor((Armor)o);
		}
		if(o.getClass() == s.getClass()) {
			this.removeSpell((Spell)o);
		}
		if(o.getClass() == p.getClass()) {
			this.removePotion((Potion)o);
		}
	}
	public boolean equals(Hero h) {
		boolean eq = true;
		if(h.name != name || h.level != level || h.xp != xp || h.mana != mana || h.healthPower != healthPower) {
			eq = false;
		}
		else if(h.agility != agility || h.strength != strength || h.dexerity != dexerity) {
			eq = false;
		}
		else if(h.weapons.equals(weapons) == false || h.spells.equals(spells)|| h.potions != potions || h.armors != armors) {
			eq = false;
		}
		return eq;
	}
	
	//Returns false if cant use potion, otherwise uses it, removes it since one use, and returns true
	public boolean usePotion(Potion p) {
		if(potions.contains(p) ==false) {
			return false;
		}
		p.applyPotion(this);
		potions.remove(p);
		return true;
		
	}
	public void setCurWeapon(Weapon w) {
		curWeapon = w;
	}
	public void setCurArmor(Armor a) {
		curArmor = a;
	}
	// Returns false if the hero doesnt own weapon, otherwise changes to it and returns true
	public boolean changeWeapon(Weapon w) {
		if(weapons.contains(w) == false) {
			return false;
		}
		else {
			curWeapon = w;
			return true;
		}
	}
	// Returns false if the hero doesnt own that armor, otherwise changes to it and returns true
	public boolean changeArmor(Armor a) {
		if(armors.contains(a) == false) {
			return false;
		}
		else {
			curArmor = a;
			return true;
		}
	}
	public void decreaseCoins(double c) {
		wallet.decreaseCoins(c);
	}
	public void increaseCoins(double c) {
		wallet.increaseCoins(c);
	}
	public void heroWin(double xpIncrease, double coinIncrease) {
		if(alive) {
			wallet.increaseCoins(coinIncrease);
			increaseXP(xpIncrease);
		}
		else {
			alive = true;
			setHP((100*level)/2);
		}
	}
	public void roundRenewal() {
		if(alive) {
			double hpInc = this.healthPower * .05;
			this.increaseHP(hpInc);
			double manaIncrease = this.mana *.05;
			this.increaseMana(manaIncrease);
		}
	}
	public void heroLoss() {
		double loss = wallet.getCoins() /2;
		if(wallet.getCoins() - loss > 0) {
			wallet.decreaseCoins(loss);
		}
		else {
			wallet.setCoins(0);
		}
	}
	
	// Abstract methods 
	
	// Set agility, strength, dexterity for heroes 
	public abstract void setSkills(double a, double s, double d);
	// Level up their skills
	public abstract void levelUp();
	
	
}
