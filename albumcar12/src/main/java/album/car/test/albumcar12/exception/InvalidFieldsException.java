package album.car.test.albumcar12.exception;

public class InvalidFieldsException extends RuntimeException{
    private String message;

    public InvalidFieldsException(String msg){
        super(msg);
        this.message = msg;
    }
}
