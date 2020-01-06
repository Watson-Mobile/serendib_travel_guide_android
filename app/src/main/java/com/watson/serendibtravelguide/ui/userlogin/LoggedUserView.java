package com.watson.serendibtravelguide.ui.userlogin;

public class LoggedUserView {
    private String displayName;
    //... other data fields that may be accessible to the UI

    LoggedUserView(String displayName) {
        this.displayName = displayName;
    }

    String getDisplayName() {
        return displayName;
    }
}
