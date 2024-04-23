package album.car.test.albumcar12.exception;

public class FieldAlreadyCreatedException extends RuntimeException{
    private String message;

    public FieldAlreadyCreatedException(String msg){
        super(msg);
        this.message = msg;
    }
}
