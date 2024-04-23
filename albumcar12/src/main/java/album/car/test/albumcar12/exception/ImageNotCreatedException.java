package album.car.test.albumcar12.exception;

public class ImageNotCreatedException extends RuntimeException{
    private String message;

    public ImageNotCreatedException(String msg){
        super(msg);
        this.message = msg;
    }
}
