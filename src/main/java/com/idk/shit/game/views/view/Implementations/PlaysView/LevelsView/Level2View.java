package com.idk.shit.game.views.view.Implementations.PlaysView.LevelsView;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

import com.idk.shit.graphics.Texture;
import com.idk.shit.graphics.TextureCache;
import com.idk.shit.game.state.State;
import com.idk.shit.game.views.view.ApplicationView;
import com.idk.shit.game.views.view.Implementations.PlaysView.*;
import com.idk.shit.objects.Meteor;

import com.idk.shit.objects.Object;
import com.idk.shit.objects.Player;
import com.idk.shit.objects.PrevMeteor;
import com.idk.shit.ui.Button;
import com.idk.shit.utils.Colours;
import com.idk.shit.utils.InputManager;
import com.idk.shit.utils.rand;

public class Level2View extends ApplicationView {
    private Deque<Object> blocks = new ArrayDeque<>();
    private Deque<Object> supposed_blocks = new ArrayDeque<>();
    private Deque<Meteor> meteors = new ArrayDeque<>();
    private Deque<PrevMeteor> prevmeteors = new ArrayDeque<>();

    public float screen_height=1000;
    public float screen_width=650;
    public float RATIO = (float)screen_width/(float)screen_height;
    private float block_height = 0.045f;
    private float block_width = 0.25f;
    private Player player;
    private Object block;
    private Meteor meteor;
    private PrevMeteor prevmeteor;

    private float speed_player_x = 0.035f;
    private float max_speed_y = 0.05f;
    private float accel_y = -0.002f;
    private int score = 0;
    private float max_height = -max_speed_y * max_speed_y / (2 * accel_y) - max_speed_y;
    private Button redButton = new Button(-0.7f, 0.95f, 0.6f, 0.1f, "menu", Colours.GREEN);
    private Texture playerTexture;
    private Texture blockTexture;
    private Texture meteorTexture;
    private Texture prevmeteorTexture;




    public Level2View(State state, long window, InputManager inputManager) {
        super( state, window, inputManager); // Передаем window в родительский класс
        initGame();
      
    }

    public void AddBlock( float left, float right, float b, float a){
        
        rand randomizer = new rand();  
        float x = randomizer.rand_x(left, right); 
        float y = (float) (Math.random() * (b - a) + a);
        int res = randomizer.rand(new int[]{1, 2}, new int[]{score, 100});
        int speed_dir= randomizer.rand(new int[]{-1, 1}, new int[]{1, 1});
        float cloudx;
        if (res == 1) {
            block = new Object(x, y, block_width, block_height, 0.006f*speed_dir, Colours.CYAN, this.blockTexture);
        } else {
            block = new Object(x, y, block_width, block_height, 0.0f, Colours.PURPLE, this.blockTexture);
        }
        blocks.addLast(block);  
        supposed_blocks.addLast(block);
        int met = randomizer.rand(new int[]{1, 2}, new int[]{1, 5});
        if (met==1){
            float xm = randomizer.rand_x(left, right); 
            meteor =new Meteor(xm, 2.3f, 0.075f, 0.075f, 0, Colours.BLACK, this.meteorTexture);
            meteors.addLast(meteor);
            cloudx= (float)randomizer.rand_x(xm-(float)(0.075/2), (float)(xm+0.075/2)); 
            prevmeteor = new PrevMeteor(cloudx, 0.9f, 0.5f, 0.16f, 0, Colours.BLACK, this.prevmeteorTexture);
            prevmeteors.addLast(prevmeteor);
        }
    }

    private void initGame() {
        playerTexture = TextureCache.getTexture("src\\main\\resources\\textures\\pngegg.png");
        blockTexture = TextureCache.getTexture("src\\main\\resources\\textures\\трава.png");
        meteorTexture = TextureCache.getTexture("src\\main\\resources\\textures\\pngwing.com (1).png");
        prevmeteorTexture = TextureCache.getTexture("src\\main\\resources\\textures\\pngwing.com (2).png");

        player = new Player(0.0f, -0.5f, 0.15f, 0.23f, 0.02f,Colours.WHITE, this.playerTexture, max_speed_y, accel_y);
        float left =  block_width / 2 - RATIO;
        float right = - block_width / 2 + RATIO; 
        AddBlock(0f, 0.001f, -0.8f, -1f);
        float prev_height;
        for (int i = 0; i < 6; ++i) {
            prev_height = supposed_blocks.getLast().getTop();
            float a = prev_height + max_height / 2;
            float b = prev_height + max_height;            
            AddBlock(left, right, b, a);

        }
    }
    
    @Override
    public void  update() throws Exception {
        if (player.fall_down()==true){
            cleanup();
            state.GameOver();
            return;
        }// поражение при падении 

        redButton.update(this.window);
        if (redButton.isClicked()||inputManager.isKeyPressed(GLFW_KEY_SPACE)) {
            state.Menu();
            cleanup();
            inputManager.cleanup();
            return;
        }// в меню при нажатии пробела     

        if (inputManager.isKeyPressed(GLFW_KEY_LEFT) && !inputManager.isKeyPressed(GLFW_KEY_RIGHT) ) {
            player.update_object(-speed_player_x);
        } else if (inputManager.isKeyPressed(GLFW_KEY_RIGHT) && !inputManager.isKeyPressed(GLFW_KEY_LEFT)) {
            player.update_object(speed_player_x);
        } else {
            player.update_object(0);
        }// обновление позиции игрока при нажатии клавиш

        for (Meteor meteor : meteors) {
            meteor.update_object(0f);
        }//обновление метеоров

        for (PrevMeteor prevmeteor : prevmeteors) {
            prevmeteor.update_object(0f);
        }//обновление prevметеоров

        for (Object block : supposed_blocks) {
            float block_speed_x = block.getSpeed_x();
            block.update_object(block_speed_x);
        }// обновление блоков

        if (player.getY() > -0.1f) {
            for (Object block : supposed_blocks) {
                block.SetY(block.getY()-(player.getY()-(-0.1f)));
            }
            for (Meteor meteor : meteors) {
                meteor.SetY(meteor.getY()-(player.getY()-(-0.1f)));
            }
            player.SetY(-0.1f);//обновлении позиций для бесконечного цикла
        }

        for (Iterator<Object> iterator = blocks.iterator(); iterator.hasNext(); ) {
            Object block = iterator.next();
            if (block.getY() < -1) {
                iterator.remove(); 
            }
        }// удаление блоков если они ниже -1

        for (Iterator<Meteor> iterator = meteors.iterator(); iterator.hasNext(); ) {
            Meteor Meteor = iterator.next();
            if (Meteor.getY() < -1) {
                iterator.remove(); 
            }// удаление метеоров если они ниже -1
        }
        for (Iterator<PrevMeteor> iterator = prevmeteors.iterator(); iterator.hasNext(); ) {
            PrevMeteor PrevMeteor = iterator.next();
            if (PrevMeteor.getY() < -1) {
                iterator.remove(); 
            }// удаление prevметеоров если они ниже -1
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
            float a = prev_height + max_height / 2;
            float b = prev_height + max_height;
            float left =  block_width / 2  - RATIO;
            float right = -block_width / 2 + RATIO;
            AddBlock(left, right, b, a);
        }

        for (Object block : blocks) {
            if ("UP"==block.collision(player)){
                player.change_speed();
                player.SetY(block.getTop()+player.height()/2);
            }
        }
        for (Meteor meteor : meteors) {
            if ("true"==meteor.collision(player)){
                player.SetY(-2f);
            }     
        }
    }
    @Override
    public void render() {
        for (Object block : blocks) {
            block.draw();
        }
        for (Meteor meteor : meteors) {
            meteor.draw();
        }

       
        redButton.draw();
        player.draw();
        for (PrevMeteor prevmeteor : prevmeteors) {
            prevmeteor.draw();
        }
    }
    @Override
    public void cleanup() {
        blocks.clear();
        supposed_blocks.clear();
        player = null;
        block = null;
        // redButton = null;
    }   
}
