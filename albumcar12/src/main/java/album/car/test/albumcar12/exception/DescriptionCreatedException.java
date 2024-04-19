package album.car.test.albumcar12.exception;

public class DescriptionCreatedException extends RuntimeException{
    private String message;

    public DescriptionCreatedException(String msg){
        super(msg);
        this.message = msg;
    }
}
