package album.car.test.albumcar12.exception;

public class ListIsEmptyException extends RuntimeException{
    private String message;

    public ListIsEmptyException(String msg){
        super(msg);
        this.message = msg;
    }
}
