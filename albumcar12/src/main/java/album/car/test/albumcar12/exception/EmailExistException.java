package album.car.test.albumcar12.exception;

public class EmailExistException extends RuntimeException{
    private String message;

    public EmailExistException(String msg){
        super(msg);
        this.message = msg;
    }
}
