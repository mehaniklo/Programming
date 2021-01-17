public class CountKidsException extends RuntimeException{
    /**
     Error that shows when the number of children is negative
     */
    public CountKidsException(String message){
        super(message);
    }
}
