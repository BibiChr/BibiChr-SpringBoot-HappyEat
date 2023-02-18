package ch.fhnw.webec.happyeat.exception.notfound;

public class HappyEatNotFoundException extends RuntimeException {

    private final String linkBack;
    private final String location;
    private static final String HEADER = "Not Found";

    public HappyEatNotFoundException(String message, String linkBack, String location) {
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
