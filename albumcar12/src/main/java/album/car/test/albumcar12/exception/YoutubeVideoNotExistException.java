package album.car.test.albumcar12.exception;

public class YoutubeVideoNotExistException extends RuntimeException{
    private String message;

    public YoutubeVideoNotExistException(String msg){
        super(msg);
        this.message = msg;
    }
}
