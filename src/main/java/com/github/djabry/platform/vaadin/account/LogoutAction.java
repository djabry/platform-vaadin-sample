/*
 * Copyright (c) 2015 Daniel Jabry
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.djabry.platform.vaadin.account;

import com.github.djabry.platform.service.api.AuthenticationService;
import com.github.djabry.platform.service.api.DomainServices;
import com.github.djabry.platform.vaadin.presenter.Sections;
import com.github.djabry.platform.vaadin.view.LoginView;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.stuff.sidebar.SideBarItem;

/**
 * @author djabry
 */

@VaadinComponent
@UIScope
@SideBarItem(sectionId = Sections.ACCOUNT, caption = "Sign out")
public class LogoutAction implements Runnable {

    @Autowired
    private EventBus eventBus;

    public void run() {

        DomainServices sP = DomainServices.getInstance();
        AuthenticationService authenticationService = sP.findService(AuthenticationService.class);
        authenticationService.logout();

        AccountEvent accountEvent = new AccountEvent();
        accountEvent.setAuthenticated(false);

        eventBus.publish(EventScope.SESSION, this, accountEvent);

        UI.getCurrent().getNavigator().navigateTo(LoginView.VIEW_NAME);
    }

}