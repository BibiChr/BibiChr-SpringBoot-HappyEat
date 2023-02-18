package ch.fhnw.webec.happyeat.exception.wronginput;

public class HappyEatWrongInputException extends RuntimeException {

    private final String linkBack;
    private final String location;
    private static final String HEADER = "There was a problem";

    public HappyEatWrongInputException(String message, String linkBack, String location) {
        super(message);
        this.linkBack = linkBack;
        this.location = location;
    }

    public String getLinkBack() {
        return linkBack;
    }

    public String getLocation() {
        return location;
    }

    public String getHeader() {
        return HEADER;
    }


}
