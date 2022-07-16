package sh.miles.yellowlib.items.immutable;

public class ImmutableViolationException extends IllegalArgumentException {
    
    public ImmutableViolationException(String message) {
        super(message);
    }

    public ImmutableViolationException(){
        super("You are trying to modify an immutable value");
    }
}
