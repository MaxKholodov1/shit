package com.idk.shit.game;

public class StateManager {
    public GameState currState;
    public void setState(GameState newState){
        if(currState!=null){
            currState.cleanup(); // Освобождаем ресурсы предыдущего состояния
            currState = null;
            currState=newState;
            System.gc();
        }
        else{
            currState = null;
            currState=newState;
            newState = null;
            System.gc();
        }
    }
    public void update(){
        if(currState!=null){
            currState.update();
        }
    }
    public void render(){
        if(currState!=null){
            currState.render();
        }
    }
}