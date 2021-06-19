package Folder.exceptions;

public class InfiniteRecursionException extends RuntimeException{
    @Override
    public String getMessage() {
        return "infinite recursion was encountered";
    }
}
