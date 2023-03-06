package bg.softuni.pathfinder.model.view;

public class UserProfileView {
    private String fullName;
    private String username;
    private Integer age;
    private String level;

    public UserProfileView(String fullName, String username, Integer age, String level) {
        this.fullName = fullName;
        this.username = username;
        this.age = age;
        this.level = level;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
