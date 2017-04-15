package model;
import java.util.List;

import controller.IController;
public class User   implements IModel { 
	 
	 
		private int id; 
	 
		private String username; 
	 
		private String password; 
	 
		private String firstName; 
	 
		private String lastName; 
	 
		private String email; 
	 
		private Date birthday; 
	 
		private Account account; 
	 
		private List<UserStock> userStocks; 
		
		public User(String name)
		{
			this.username = name;
		}
		public User(){}
	 
		public final int getId() {
			return this.id;
		} 
		public final void setId(final int someId) {
			this.id = someId;
		} 
	 
		public final String getUsername() {
			return this.username;
		} 
		public final void setUsername(final String someUsername) {
			this.username = someUsername;
		} 
	 
		public final String getPassword() {
			return this.password;
		} 
		public final void setPassword(final String somePassword) {
			this.password = somePassword;
		} 
	 
		public final String getFirstName() {
			return this.firstName;
		} 
		public final void setFirstName(final String someFirstName) {
			this.firstName = someFirstName;
		} 
	 
		public final String getLastName() {
			return this.lastName;
		} 
		public final void setLastName(final String someLastName) {
			this.lastName = someLastName;
		} 
	 
		public final String getEmail() {
			return this.email;
		} 
		public final void setEmail(final String someEmail) {
			this.email = someEmail;
		} 
	 
		public final Date getBirthday() {
			return this.birthday;
		} 
		public final void setBirthday(final Date someBirthday) {
			this.birthday = someBirthday;
		} 
	 
		public final Account getAccount() {
			return this.account;
		} 
		public final void setAccount(final Account someAccount) {
			this.account = someAccount;
		} 

		@Override
		public IController getController() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public void setController(IController some) {
			// TODO Auto-generated method stub
			
		} 
	 
} 