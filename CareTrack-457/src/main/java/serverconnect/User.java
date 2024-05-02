package serverconnect;

public class User {
  public String userID; 

  User(String user){
      this.userID = user;
}


  public String getCurrentUser(){
      return this.userID;
} 

  public void setCurrentUser(String user){
     this.userID = user;
}   
 }