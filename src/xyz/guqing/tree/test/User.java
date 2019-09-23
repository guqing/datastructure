package xyz.guqing.tree.test;

public class User implements Comparable<User>{
	private Integer id;
	private String username;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + "]";
	}

	@Override
	public int compareTo(User o) {
		return id - o.id;
	}
	
}
