package album.car.test.albumcar12.model;

public enum UserRole {
    ADMIN("admin"),
    NORMAL("normal");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return this.role;
    }
}
