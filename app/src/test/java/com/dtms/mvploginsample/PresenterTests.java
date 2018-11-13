package com.dtms.mvploginsample;

import com.dtms.mvploginsample.login.LoginActivityMVP;
import com.dtms.mvploginsample.login.LoginActivityPresenter;
import com.dtms.mvploginsample.login.User;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class PresenterTests {

    LoginActivityMVP.View mockView;
    LoginActivityMVP.Model mockLoginModel;
    LoginActivityPresenter presenter;
    User user;

    @Before
    public void setup(){
        mockLoginModel = mock(LoginActivityMVP.Model.class);

        user = new User("John", "Doe");

//        when(mockLoginModel.getUser()).thenReturn(user);

        mockView = mock(LoginActivityMVP.View.class);

        presenter = new LoginActivityPresenter(mockLoginModel);

        presenter.setView(mockView);
    }

    @Test
    public void loadTheUserFromTheRepositoryWhenValidUserIsPresent(){

        when(mockLoginModel.getUser()).thenReturn(user);

        presenter.getCurrentUser();

        //verify model interaction
        verify(mockLoginModel, times(1)).getUser();

        //verify view interaction
        verify(mockView, times(1)).setFirstName("John");
        verify(mockView, times(1)).setLastName("Doe");
        verify(mockView, never()).showUserNotAvailable();
    }

    @Test
    public void shouldShowErrorMessageWhenUserIsNull(){
        when(mockLoginModel.getUser()).thenReturn(null);

        presenter.getCurrentUser();

        //verify model interaction
        verify(mockLoginModel, times(1)).getUser();

        //verify view interaction
        verify(mockView, never()).setFirstName("John");
        verify(mockView, never()).setLastName("Doe");
        verify(mockView, times(1)).showUserNotAvailable();
    }

    @Test
    public void shouldCreateErrorMessageIfFieldAreEmpty(){
        //Set up the view mock
        when(mockView.getFirstName()).thenReturn("");

        presenter.loginButtonClicked();

        verify(mockView, times(1)).getFirstName();
        verify(mockView, never()).getLastName();
        verify(mockView, times(1)).showInputError();

        //Now tell mockView to return a value for first name and an empty last name
        when(mockView.getFirstName()).thenReturn("John");
        when(mockView.getLastName()).thenReturn("");

        presenter.loginButtonClicked();

        verify(mockView, times(2)).getFirstName();
        verify(mockView, times(1)).getLastName();
        verify(mockView, times(2)).showInputError();
    }

    @Test
    public void shouldBeAbleToSaveAValidUser(){
        when(mockView.getFirstName()).thenReturn("John");
        when(mockView.getLastName()).thenReturn("Doe");

        presenter.loginButtonClicked();

        verify(mockView, times(2)).getFirstName();
        verify(mockView, times(2)).getLastName();

        //Make sure the repository saved the user
        verify(mockLoginModel, times(1)).createUser("John", "Doe");

        //Make sure the view showed the user saved message
        verify(mockView, times(1)).showUserSavedMessage();
    }
}
