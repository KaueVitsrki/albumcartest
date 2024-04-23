package album.car.test.albumcar12.exception;

public class DescriptionNotCreatedException extends RuntimeException{
    private String message;

    public DescriptionNotCreatedException(String msg){
        super(msg);
        this.message = msg;
    }
}
