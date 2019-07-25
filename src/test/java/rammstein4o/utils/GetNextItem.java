package rammstein4o.utils;

import java.util.Map;
import java.util.concurrent.Callable;

public final class GetNextItem implements Callable<Boolean> {
    private final ResponseType responseType;
    private final MySessionHandler sessionHandler;
    private Map item;

    public GetNextItem(MySessionHandler sessionHandler, ResponseType responseType) {
        this.sessionHandler = sessionHandler;
        this.responseType = responseType;
    }

    public Boolean call() throws Exception {
        item = sessionHandler.getNext(responseType);
        return item != null;
    }

    public Map getItem() {
        return item;
    }
}