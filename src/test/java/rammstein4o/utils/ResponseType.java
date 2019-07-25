package rammstein4o.utils;

public enum ResponseType {
    AUTHENTICATE("AUTHENTICATE"),
    SOCKET_READY("SOCKET_READY"),
    ACCOUNT_CHANGE_BALANCE("ACCOUNT_CHANGE_BALANCE"),
    OTHER("OTHER");

    private String type;

    ResponseType(String myType) {
        this.type = myType;
    }

    public String getUrl() {
        return type;
    }
}