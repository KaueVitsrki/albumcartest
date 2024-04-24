package album.car.test.albumcar12.exception;

public class ImageNotExistException extends RuntimeException{
    private String message;

    public ImageNotExistException(String msg){
        super(msg);
        this.message = msg;
    }
}
