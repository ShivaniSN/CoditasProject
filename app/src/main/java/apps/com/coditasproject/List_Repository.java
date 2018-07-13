package apps.com.coditasproject;

public class List_Repository {
    String stringRepositoryName,stringRepositoryDescription,stringRepositoryURL,stringLanguage,stringCreatedOn;

    public List_Repository(){
        this.stringCreatedOn = "";
        this.stringRepositoryName = "";
        this.stringRepositoryDescription = "";
        this.stringRepositoryURL = "";
        this.stringLanguage = "";
    }

    public String getStringCreatedOn() {
        return stringCreatedOn;
    }

    public void setStringCreatedOn(String stringCreatedOn) {
        this.stringCreatedOn = stringCreatedOn;
    }

    public String getStringLanguage() {
        return stringLanguage;
    }

    public void setStringLanguage(String stringLanguage) {
        this.stringLanguage = stringLanguage;
    }

    public String getStringRepositoryDescription() {
        return stringRepositoryDescription;
    }

    public void setStringRepositoryDescription(String stringRepositoryDescription) {
        this.stringRepositoryDescription = stringRepositoryDescription;
    }

    public String getStringRepositoryName() {
        return stringRepositoryName;
    }

    public void setStringRepositoryName(String stringRepositoryName) {
        this.stringRepositoryName = stringRepositoryName;
    }

    public String getStringRepositoryURL() {
        return stringRepositoryURL;
    }

    public void setStringRepositoryURL(String stringRepositoryURL) {
        this.stringRepositoryURL = stringRepositoryURL;
    }
}
