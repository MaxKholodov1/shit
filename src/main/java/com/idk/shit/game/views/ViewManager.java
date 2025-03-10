package com.idk.shit.game.views;

import com.idk.shit.game.state.State;
import com.idk.shit.game.state.ValueObjects.ApplicationState;
import com.idk.shit.game.state.ValueObjects.Implementations.GameOver;
import com.idk.shit.game.state.ValueObjects.Implementations.Plays.Play;

import com.idk.shit.game.state.ValueObjects.Implementations.Menu;
import com.idk.shit.game.views.view.ApplicationView;
import com.idk.shit.game.views.view.Implementations.GameOverView;
import com.idk.shit.game.views.view.Implementations.MenuView;
import com.idk.shit.game.views.view.Implementations.PlayingView;
import com.idk.shit.utils.InputManager;

public class ViewManager {
    public State currentState;
    public ApplicationView currentView;
    public long window;
    public InputManager inputManager;
    public ViewManager(long window, InputManager inputManager, State currentState){
        this.window=window;
        this.inputManager=inputManager;
        this.currentState=currentState;
    }
    public void setState(ApplicationView newView){
        if(currentView!=null){
            currentView.cleanup(); // Освобождаем ресурсы предыдущего состояния
            currentView = null;
            currentView=newView;
            System.gc();
        }
        else{
            currentView = null;
            currentView=newView;
            newView = null;
            System.gc();
        }
    }
    public void update(){
        if(currentView!=null){
            if(currentView!=null){
                try {
                    currentView.update();  // Вызов метода, который может выбросить Exception
                } catch (Exception e) {
                    System.out.println("Обнаружена ошибка: " + e.getMessage());
                }
            }
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
        if(state instanceof Menu && !(currentView instanceof MenuView)){
            currentView = new MenuView(currentState, window, inputManager );
        }

        if(state instanceof GameOver && !(currentView instanceof GameOverView)){
            currentView = new GameOverView(currentState,window, inputManager );
        }
        if (state instanceof Play && !(currentView instanceof PlayingView)){
            currentView = new PlayingView(currentState ,window, inputManager );
        }
    }
}