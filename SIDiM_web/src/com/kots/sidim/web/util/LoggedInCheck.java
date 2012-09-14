package com.kots.sidim.web.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;


public class LoggedInCheck implements PhaseListener {

    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    public void beforePhase(PhaseEvent event) {
    }

    public void afterPhase(PhaseEvent event) {
        FacesContext fc = event.getFacesContext();
        boolean loginPage = fc.getViewRoot().getViewId().lastIndexOf("login") > -1 ? true : false;
        if (!loginPage && !loggedIn()) {
            NavigationHandler nh = fc.getApplication().getNavigationHandler();
            nh.handleNavigation(fc, null, "logout");
        }
    }

    private boolean loggedIn() {
        //return LoginBean.isLoggedIn();
    	return false;
    }
} 
