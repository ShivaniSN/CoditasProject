package apps.com.coditasproject;

import android.support.annotation.NonNull;

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

    public static final Comparable<List_Users> BY_NAME_ALPHABETICAL = new Comparable<List_Users>() {
        @Override
        public int compareTo(List_Users o) {
            return o.stringUserName.compareTo(o.stringUserName);
        }
    };
}
