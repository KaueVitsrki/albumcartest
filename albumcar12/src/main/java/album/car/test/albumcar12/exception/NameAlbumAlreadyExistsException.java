package album.car.test.albumcar12.exception;

public class NameAlbumAlreadyExistsException extends RuntimeException{
    private String message;

    public NameAlbumAlreadyExistsException(String msg){
        super(msg);
        this.message = msg;
    }
}
