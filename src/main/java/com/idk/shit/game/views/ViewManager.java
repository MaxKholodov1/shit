package com.idk.shit.game.views;

import com.idk.shit.game.state.State;
import com.idk.shit.game.state.ValueObjects.ApplicationState;
import com.idk.shit.game.state.ValueObjects.Implementations.GameOver;
import com.idk.shit.game.state.ValueObjects.Implementations.Menu;
import com.idk.shit.game.views.view.ApplicationView;
import com.idk.shit.game.views.view.Implementations.GameOverView;
import com.idk.shit.game.views.view.Implementations.MenuView;

public class ViewManager {
    public State currentState;
    public ApplicationView currentView;
    public void setState(ApplicationView newState){
        if(currentView!=null){
            currentView.cleanup(); // Освобождаем ресурсы предыдущего состояния
            currentView = null;
            currentView=newState;
            System.gc();
        }
        else{
            currentView = null;
            currentView=newState;
            newState = null;
            System.gc();
        }
    }
    public void update(){
        if(currentView!=null){
            currentView.update();
        }
        UpdateViewByState();
    }
    public void render(){
        if(currentView!=null){
            currentView.render();
        }
    }

    private void UpdateViewByState(){
        ApplicationState state = currentState.GetApplicationState();
        if(state instanceof Menu){
            currentView = new MenuView(currentState, windows, );
        }

        if(state instanceof GameOver){
            currentView = new GameOverView();
        }
    }
}