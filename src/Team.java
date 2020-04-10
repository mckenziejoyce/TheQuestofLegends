import java.util.ArrayList;
import java.util.List;

public class Team {
	private List<LivingCreature> members;
	private int numOfMembers;
	private String teamName;
	private boolean winner;
	
	public Team() {
		members = new ArrayList<LivingCreature>();
		setNumOfMembers();
		teamName = "";
		winner = false;
	}
	public Team(String name) {
		this();
		setName(name);
	}
	public Team(String name, List<LivingCreature> mems) {
		this();
		setName(name);
		members = mems;
	}
	public List<LivingCreature> getMembers() {
		return members;
	}
	public void setNumOfMembers() {
		numOfMembers = members.size();
	}
	public void addMember(LivingCreature m) {
		members.add(m);
		setNumOfMembers();
	}
	public boolean deleteMember(LivingCreature m) {
		boolean rem = members.remove(m);
		setNumOfMembers();
		return rem;
	}
	public double getNumOfMembers() {
		return numOfMembers;
	}
	public void setWinner() {
		winner = true;
	}
	public boolean isWinner() {
		return winner;
	}
	public void setName(String name) {
		this.teamName = name;
	}
	public String getTeamName() {
		return teamName;
	}
	

}
