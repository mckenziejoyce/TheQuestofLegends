
public class Dragon extends Monster{
	private double defaultBD = 100;
	private double defaultDlevel = 90;
	private double defaultdodgechance = 30;
	private double defaultLevel = 3;
	
	public Dragon() {
		super();
		setSkills(defaultBD,defaultDlevel,defaultdodgechance);
		setLevel(defaultLevel);
	}
	public Dragon(double bd, double d, double dc) {
		this();
		setSkills(bd,d,dc);
	}
	public Dragon(double bd, double d, double dc, double level) {
		this();
		setSkills(bd,d,dc);
		setLevel(level);
	}
	public Dragon(double bd, double d, double dc, double level, String name) {
		this();
		setSkills(bd,d,dc);
		setLevel(level);
		setName(name);
	}
	public void setSkills(double baseDamage, double defense, double dodgeChance) {
		if(baseDamage <= defense || baseDamage <= dodgeChance) {
			throw new IllegalArgumentException();
		}
		setBaseDamage(baseDamage);
		setDefenseLevel(defense);
		setDodgeChance(dodgeChance);
	}
	public String toString() {
		String ret = "This dragon's name is "+ this.getName();
		ret += "\n Base Damage: "+this.getBaseDamage();
		ret += " Defense Level: "+this.getDefenseLevel();
		ret += " HP: "+this.getHP();
		ret += " Level: "+this.getLevel();
		return ret;
	}
	
}
