package model;

public class User<T> {

    // <editor-fold desc="Class Fields">
    private T firstName;

    private T lastName;

    private T email;

    private T password;

    private T confirmPassword;

    private T group;
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
    // </editor-fold>

}
