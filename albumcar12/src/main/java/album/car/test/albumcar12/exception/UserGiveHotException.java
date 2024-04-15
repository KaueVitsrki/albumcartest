package album.car.test.albumcar12.exception;

public class UserGiveHotException extends RuntimeException{
    private String message;

    public UserGiveHotException(String msg){
        super(msg);
        this.message = msg;
    }
}
