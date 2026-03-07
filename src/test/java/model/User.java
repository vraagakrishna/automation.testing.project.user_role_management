package model;

public class User<T> {

    // <editor-fold desc="Class Fields">
    private T firstName;

    private T lastName;

    private T email;

    private T oldPassword;

    private T password;

    private T confirmPassword;

    private T group;

    private String token;
    // </editor-fold>

    // <editor-fold desc="Ctor">
    public User() {
    }

    public User(T email, T password) {
        this.email = email;
        this.password = password;
    }
    // </editor-fold>

    // <editor-fold desc="Getters and Setters">
    public T getFirstName() {
        return firstName;
    }

    public void setFirstName(T firstName) {
        this.firstName = firstName;
    }

    public T getLastName() {
        return lastName;
    }

    public void setLastName(T lastName) {
        this.lastName = lastName;
    }

    public T getEmail() {
        return email;
    }

    public void setEmail(T email) {
        this.email = email;
    }

    public T getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(T oldPassword) {
        this.oldPassword = oldPassword;
    }

    public T getPassword() {
        return password;
    }

    public void setPassword(T password) {
        this.password = password;
    }

    public T getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(T confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public T getGroup() {
        return group;
    }

    public void setGroup(T group) {
        this.group = group;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    // </editor-fold>

    // <editor-fold desc="Overrides">
    @Override
    public String toString() {
        return "User{" +
                "firstName=" + firstName +
                ", lastName=" + lastName +
                ", email=" + email +
                ", oldPassword=" + oldPassword +
                ", password=" + password +
                ", confirmPassword=" + confirmPassword +
                ", group=" + group +
                '}';
    }
    // </editor-fold>

}
