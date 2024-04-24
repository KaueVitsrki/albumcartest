package album.car.test.albumcar12.exception;

public class ImageExistException extends RuntimeException{
    private String message;

    public ImageExistException(String msg){
        super(msg);
        this.message = msg;
    }
}
