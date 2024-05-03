package serverconnect;

public class User {
  public String userID;
  public String userType; 

  public User(String user, String type){
    this.userID = user;
    this.userType = type;
  }


  public String getCurrentUser(){
    return this.userID;
  } 

  public void setCurrentUser(String user){
    this.userID = user;
  }   

  public String getCurrentUserType() {
    return this.userType;
  }
}
