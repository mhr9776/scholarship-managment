package ir.mctab.java32.projects.scholarshipmanagement.features.usermanagement.impl;

import ir.mctab.java32.projects.scholarshipmanagement.core.share.AuthenticationService;

public class LogoutUseCaseImpl {
    public void logout() {
        AuthenticationService.getInstance().setLoginUser(null);
    }
}
