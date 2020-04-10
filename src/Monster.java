import java.util.Random;

public abstract class Monster extends LivingCreature implements Fightable {
	
	private double defaultBD = 100;
	private double defaultDlevel = 90;
	private double defaultdodgechance = 30;
	private String defaultName = "Monster";
	private double defaultLevel = 3;
	private double baseDamage;
	private double defenseLevel;
	private double dodgeChance;
	private boolean isFighting;
	
	public Monster() {
		super();
		setBaseDamage(defaultBD);
		setDefenseLevel(defaultDlevel);
		setDodgeChance(defaultdodgechance);
		setName(defaultName);
		setLevel(defaultLevel);
	}
	public double getBaseDamage() {
		return this.baseDamage;
	}
	public double getDefenseLevel() {
		return this.defenseLevel;
	}
	public double getDodgeChance() {
		return this.dodgeChance;
	}
	public boolean isFighting() {
		return isFighting;
	}
	public void startedFight() {
		isFighting = true;
	}
	public void endedFight() {
		isFighting = false;
	}
	public void setBaseDamage(double b) {
		if(b < 0) {
			throw new IllegalArgumentException();
		}
		this.baseDamage = b;
	}
	public void setDefenseLevel(double b) {
		if(b < 0) {
			throw new IllegalArgumentException();
		}
		this.defenseLevel = b;
	}
	public void setDodgeChance(double b) {
		if(b < 0) {
			throw new IllegalArgumentException();
		}
		this.dodgeChance = b;
	}
	
	public void getAttacked(double d) {
		Random rand = new Random();
		int i = rand.nextInt(101);
		if(i <= dodgeChance) {
			return;
		}
		if(d-defenseLevel <0) {
			return;
		}
		decreaseHP(d-defenseLevel);
	}
	public void getSpellEffect(Hero hero,Spell spell) {
		double finalDamage = spell.getBaseDamage() + (hero.getDexerity()/10000)*spell.getBaseDamage();
		if(spell.getSpellType().compareTo("Fire")==0) {
			this.getAttacked(finalDamage);
			this.setDefenseLevel(getDefenseLevel()-(getDefenseLevel()*.1));
		}
		else if(spell.getSpellType().compareTo("Ice")==0) {
			this.getAttacked(finalDamage);
			this.setBaseDamage(getBaseDamage()-(getBaseDamage()*.1));
		}
		else if(spell.getSpellType().compareTo("Lightning")==0) {
			this.getAttacked(finalDamage);
			this.setDodgeChance(this.getDodgeChance()-(this.getDodgeChance()*.1));
		}
		hero.decreaseMana(spell.getManaNeeded());
	}

	public abstract void setSkills(double baseDamage, double defense, double dodgeChance);
}
