
public class Spirit extends Monster{
	private double defaultBD = 90;
	private double defaultDlevel = 80;
	private double defaultdodgechance = 60;
	private double defaultLevel = 3;
	
	public Spirit() {
		super();
		setSkills(defaultBD,defaultDlevel,defaultdodgechance);
		setLevel(defaultLevel);
	}
	public Spirit(double bd, double d, double dc) {
		this();
		setSkills(bd,d,dc);
	}
	public Spirit(double bd, double d, double dc, double level) {
		this();
		setSkills(bd,d,dc);
		setLevel(level);
	}
	public Spirit(double bd, double d, double dc, double level, String name) {
		this();
		setSkills(bd,d,dc);
		setLevel(level);
		setName(name);
	}
	public void setSkills(double baseDamage, double defense, double dodgeChance) {
//		if(dodgeChance <= baseDamage || dodgeChance <= defense) {
//			throw new IllegalArgumentException();
//		}
		setBaseDamage(baseDamage);
		setDefenseLevel(defense);
		setDodgeChance(dodgeChance);
	}
	public String toString() {
		String ret = "This spirit's name is "+ this.getName();
		ret += "\n Base Damage: "+this.getBaseDamage();
		ret += " Defense Level: "+this.getDefenseLevel();
		ret += " HP: "+this.getHP();
		ret += " Level: "+this.getLevel();
		return ret;
	}
}
