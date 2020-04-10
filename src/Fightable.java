
public interface Fightable {
	public abstract boolean isFighting();
	public abstract void startedFight();
	public abstract void endedFight();
	public abstract void getAttacked(double d);
	public abstract boolean isAlive();
	
}
