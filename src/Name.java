/* McKenzie Joyce 
 * Feb 2020 
 * Name.java : Create a name, allows for firstName, last Name, middleInitial, nickname, prefix, suffix, and username
 * 	Name() : Creates a Name with everything = to an empty string (default)
 * 	Name(String name) : Sets username = name
 *  Name(String fName, String lName) : Sets first and last name
 *  Name(String fName, String lName, String mI) : sets first name, last name, and middle initial
 *  Name(String fName, String lName, String mI, String nN) : Sets first name, last name, middle initial, and nickname
 */
public class Name {
	String firstName;
	String lastName; 
	String middleInitial;
	String nickname;
	String prefix;
	String suffix;
	String username;
	
	public Name() {
		firstName = "";
		lastName = ""; 
		middleInitial="";
		nickname="";
		prefix="";
		suffix="";
		username="";
	}
	
	public Name(String name) {
		this();
		username = name;
	}
	public Name(String fName, String lName) {
		this();
		firstName = fName;
		lastName = lName;
	}
	public Name(String fName, String lName, String mI) {
		this();
		firstName = fName;
		lastName = lName; 
		middleInitial = mI;
	}
	public Name(String fName, String lName, String mI, String nN) {
		this();
		firstName = fName;
		lastName = lName; 
		middleInitial = mI;
		nickname = nN; 
	}
	public Name(String fName, String lName, String mI, String nN, String pre) {
		this();
		firstName = fName;
		lastName = lName; 
		middleInitial = mI;
		nickname = nN;
		prefix = pre;
	}
	public Name(String fName, String lName, String mI, String nN, String pre, String suff) {
		this();
		firstName = fName;
		lastName = lName; 
		middleInitial = mI;
		nickname = nN;
		prefix = pre;
		suffix = suff; 
	}
	public String toString() {
		String ret = firstName+lastName+middleInitial+nickname+prefix+suffix+username;
		return ret;
		
	}
}
