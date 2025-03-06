package com.idk.shit.game;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import com.idk.shit.graphics.Texture;

import com.idk.shit.objects.Object;
import com.idk.shit.objects.Player;
import com.idk.shit.ui.button;
import com.idk.shit.utils.Colours;
import com.idk.shit.utils.InputManager;
import com.idk.shit.utils.ScoreManager;
import com.idk.shit.utils.rand;


public class Game extends GameState {
    private Deque<Object> blocks = new ArrayDeque<>();
    private Deque<Object> supposed_blocks = new ArrayDeque<>();

    private float block_height = 0.04f;
    private float block_width = 0.35f;
    private Player player;
    private Object block;
   
    private Texture blockTexture;
    private Texture playerTexture;
    private float speed_player_x = 0.05f;
    private float max_speed_y = 0.06f;
    private float accel_y = -0.003f;
    private int score = 0;
    private float max_height = -max_speed_y * max_speed_y / (2 * accel_y) - 0.05f;
    private button redButton = new button(-0.7f, 0.95f, 0.6f, 0.1f, "menu", Colours.GREEN);



    public Game(long window,InputManager inputManager, Texture blockTexture, Texture playerTexture) {
        super( window,State._game_, inputManager); // Передаем window в родительский класс
        this.blockTexture = blockTexture;
        this.playerTexture = playerTexture;

        initGame();
      
    }

    public void AddBlock( float left, float right, float b, float a){
        
        rand randomizer = new rand();  
        float x = randomizer.rand_x(left, right); 
        float y = (float) (Math.random() * (b - a) + a);
        int res = randomizer.rand(new int[]{1, 2}, new int[]{score, 100});
        int speed_dir= randomizer.rand(new int[]{-1, 1}, new int[]{1, 1});
        if (res == 1) {
            block = new Object(x, y, block_width, block_height, 0.006f*speed_dir, Colours.CYAN, this.blockTexture);
        } else {
            block = new Object(x, y, block_width, block_height, 0.0f, Colours.PURPLE, this.blockTexture);
        }
        blocks.addLast(block);  
        supposed_blocks.addLast(block);
    }

    private void initGame() {
        player = new Player(0.0f, 0.0f, 0.2f, 0.13f, 0.02f,Colours.WHITE, this.playerTexture);
        block = new Object(0.0f, -0.5f, block_width, block_height, 0.0f, Colours.PURPLE, this.blockTexture );
        float left = -1 + block_width / 2;
        float right = 1 - block_width / 2;
        AddBlock(0f, 0.001f, -0.4f, -0.5f);
        float prev_height;
        for (int i = 0; i < 6; ++i) {
            prev_height = supposed_blocks.getLast().getTop();
            float a = prev_height + max_height / 5;
            float b = prev_height + max_height;
            AddBlock(left, right, b, a);

        }
    }
    
    @Override
    public State update() {
        if (player.fall_down()==true){
            cleanup();
            this.curState = State._overgame_;
            return this.curState;
        }
        if (inputManager.isKeyPressed(GLFW_KEY_LEFT)) {
            player.update_object(-speed_player_x);
        } else if (inputManager.isKeyPressed(GLFW_KEY_RIGHT)) {
            player.update_object(speed_player_x);
        } else {
            player.update_object(0);
        }

        for (Object block : supposed_blocks) {
            float block_speed_x = block.getSpeed_x();
            block.update_object(block_speed_x);
        }

        if (player.getY() > 0) {
            for (Object block : supposed_blocks) {
                block.SetY(block.getY()-player.getY());
            }
            player.SetY(0f);
        }

        for (Iterator<Object> iterator = blocks.iterator(); iterator.hasNext(); ) {
            Object block = iterator.next();
            if (block.getY() < -1) {
                iterator.remove(); 
            }
        }

        int cnt = 0;
        for (Iterator<Object> iterator = supposed_blocks.iterator(); iterator.hasNext(); ) {
            Object block = iterator.next();
            if (block.getY() < -1) {
                cnt++;
                score++;
                iterator.remove(); 
            }
        }

        float prev_height;
        for (int i = 0; i < cnt; ++i) {
            prev_height = supposed_blocks.getLast().getY();
            float a = prev_height + max_height / 3;
            float b = prev_height + max_height;
            float left = -1 + block_width / 2;
            float right = 1 - block_width / 2;
            AddBlock(left, right, b, a);
        }

        for (Object block : blocks) {
            block.collision(player);
        }

        redButton.update(this.window);
        if ( redButton.isClicked() || inputManager.isKeyPressed(GLFW_KEY_SPACE)) {
            if(score>ScoreManager.Load()){
                ScoreManager.savebest_attamp(score);
            }
            blocks.clear();
            supposed_blocks.clear();
            cleanup();
            inputManager.cleanup();
            this.curState = State._menu_;
            return this.curState;
        }
        return this.curState;
    }
    @Override
    public void render() {
        for (Object block : blocks) {
            block.draw();
        }
        redButton.draw();
        player.draw();
    }
    @Override
    public void cleanup() {
        blocks.clear();
        supposed_blocks.clear();
        player = null;
        block = null;
        redButton = null;
    }   
}