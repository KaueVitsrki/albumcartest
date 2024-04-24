package album.car.test.albumcar12.exception;

public class MaxListSizeException extends RuntimeException{
    private String message;

    public MaxListSizeException(String msg){
        super(msg);
        this.message = msg;
    }
}
