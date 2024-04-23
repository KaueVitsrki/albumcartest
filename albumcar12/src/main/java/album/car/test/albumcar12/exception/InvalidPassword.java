package album.car.test.albumcar12.exception;

public class InvalidPassword extends RuntimeException{
    private String message;

    public InvalidPassword(String msg){
        super(msg);
        this.message = msg;
    }
}
