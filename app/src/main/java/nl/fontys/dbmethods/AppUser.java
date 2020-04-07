package nl.fontys.dbmethods;

public class AppUser {
    private String postfirstName;
    private String postlastName;
    private String postusername;
    private String postpassword;
    private String postemail;
    private String postbirthday;
    private String postaddress;
    private String postgender;

    public AppUser(String postfirstName, String postlastName, String postusername,
                   String postpassword, String postemail, String postbirthday, String postaddress,
                   String postgender
                   ) {
        this.postfirstName = postfirstName;
        this.postlastName = postlastName;
        this.postusername = postusername;
        this.postpassword = postpassword;
        this.postemail = postemail;
        this.postbirthday = postbirthday;
        this.postaddress = postaddress;
        this.postgender = postgender;
    }

    public String getPostfirstName() {
        return postfirstName;
    }

    public void setPostfirstName(String postfirstName) {
        this.postfirstName = postfirstName;
    }

    public String getPostlastName() {
        return postlastName;
    }

    public void setPostlastName(String postlastName) {
        this.postlastName = postlastName;
    }

    public String getPostusername() {
        return postusername;
    }

    public void setPostusername(String postusername) {
        this.postusername = postusername;
    }

    public String getPostpassword() {
        return postpassword;
    }

    public void setPostpassword(String postpassword) {
        this.postpassword = postpassword;
    }

    public String getPostemail() {
        return postemail;
    }

    public void setPostemail(String postemail) {
        this.postemail = postemail;
    }

    public String getPostbirthday() {
        return postbirthday;
    }

    public void setPostbirthday(String postbirthday) {
        this.postbirthday = postbirthday;
    }

    public String getPostaddress() {
        return postaddress;
    }

    public void setPostaddress(String postaddress) {
        this.postaddress = postaddress;
    }

    public String getPostgender() {
        return postgender;
    }

    public void setPostgender(String postgender) {
        this.postgender = postgender;
    }


}

