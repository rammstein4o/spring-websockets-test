package rammstein4o;

import java.util.Date;

public class Response {
    public String type;
    public Object payload;
    public Long timestamp;

    public Response() {
        this.timestamp = new Date().getTime();
    }
}


