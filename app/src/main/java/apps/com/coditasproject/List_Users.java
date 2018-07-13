package apps.com.coditasproject;

public class List_Users {

    String stringUserName,stringId,stringProfileImage,stringScore;

    public void List_Users(){
        this.stringId = "";
        this.stringUserName = "";
        this.stringProfileImage = "";
        this.stringScore = "";
    }

    public String getStringUserName() {
        return stringUserName;
    }

    public void setStringUserName(String stringUserName) {
        this.stringUserName = stringUserName;
    }

    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
    }

    public String getStringProfileImage() {
        return stringProfileImage;
    }

    public void setStringProfileImage(String stringProfileImage) {
        this.stringProfileImage = stringProfileImage;
    }

    public String getStringScore() {
        return stringScore;
    }

    public void setStringScore(String stringScore) {
        this.stringScore = stringScore;
    }
}
