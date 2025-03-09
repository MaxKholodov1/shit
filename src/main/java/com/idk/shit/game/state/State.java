package com.idk.shit.game.state;

import com.idk.shit.game.state.ValueObjects.ApplicationState;
import com.idk.shit.game.state.ValueObjects.Implementations.Menu;
import com.idk.shit.game.state.ValueObjects.Implementations.Plays.Play;
import com.idk.shit.game.state.ValueObjects.Implementations.GameOver;

public class State {
    private ApplicationState CurrentState;

    public ApplicationState GetApplicationState(){
        return CurrentState;
    }

    public State(){
        CurrentState = new Menu();
    }

    public void Play() throws Exception{
        if((CurrentState instanceof Menu) ||
        (CurrentState instanceof GameOver)){
            CurrentState = new Play();
        }else{
            throw new Exception();
        }
        
    }

    public void GameOver() throws Exception{
        if(CurrentState instanceof Play){
            CurrentState = new GameOver();
        }else{
            throw new Exception();
        }
        
    }
    public void Menu() throws Exception{
        if(CurrentState instanceof Play){
            CurrentState = new Menu();
        }else{
            throw new Exception();
        }
        
    }
}
