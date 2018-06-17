package dto;

/**
 * @author hemanth.shivashankrappa on 16/06/2018
 * @project interview-test
 */

/**
 * This class is used to store user credentials
 *
 * @param: username and password
 */
public class UserDTO {
	private String username;
	private String password;

	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
