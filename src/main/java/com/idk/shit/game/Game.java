package com.idk.shit.game;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL11;

import com.idk.shit.objects.Object;
import com.idk.shit.objects.Player;
import com.idk.shit.utils.Colours;
import com.idk.shit.utils.InputManager;
import com.idk.shit.utils.ScoreManager;
// import com.idk.shit.utils.rand;


public class Game extends GameState {
    private Deque<Object> blocks = new ArrayDeque<>();
    private Deque<Object> supposed_blocks = new ArrayDeque<>();

    private float block_height = 0.04f;
    private float block_width = 0.35f;

    long vg = NanoVGGL3.nvgCreate(NanoVG.NVG_ALIGN_BASELINE);
    // protected String [] textures = new String[]{"src\\main\\resources\\textures\\photo_2025-03-03_11-41-26.jpg.png", "src\\main\\resources\\textures\\photo_2025-03-03_11-41-26.jpg.png"};
    // private String path_to_block_texture="src\\main\\resources\\textures\\photo_2025-03-03_11-41-26.jpg.png";
    // private String path_to_player_texture="src\\main\\resources\\textures\\pngegg.png";

    private Player player = new Player(0.0f, 0.0f, 0.2f, 0.13f, 0.02f,Colours.WHITE );
    private Object block = new Object(0.0f, -0.5f, block_width, block_height, 0.0f, Colours.PURPLE);
    // private button redButton = new button(-0.7f, 0.95f, 0.6f, 0.1f, "menu", Colours.GREEN, vg);
    
    // private  TextRenderer text_score = new TextRenderer(0.8f, 0.8f, "", Colours.BLACK, vg, 0.2f,0.4f );



    private float speed_player_x = 0.05f;

    private float max_speed_y = 0.06f;
    private float accel_y = -0.003f;

    private int score = 0;

    private float max_height = -max_speed_y * max_speed_y / (2 * accel_y) - 0.05f;

    public Game(long window, InputManager inputManager) {
        super(window, State._game_, inputManager); // Передаем window в родительский класс
        this.inputManager = inputManager;
        initGame();
        String label = String.valueOf(score);
        // this.text_score = new TextRenderer(0.8f, 0.8f, label, Colours.BLACK, vg, 0.2f, 0.4f);
        // for (String texturePath : textures) {
        //     TextureCache.getTexture(texturePath); // здесь структуры загружаются через TExtureCache в Map
        // }
    }

    public void AddBlock( float left, float right, float b, float a){
        // rand randomizer = new rand();  
        // int texture_number= (int)(Math.random() * (textures.length- 0) + 0);// используем рандом чтобы получить случайную текстуру блока
        // path_to_block_texture=textures[texture_number];
        float x = 0;
        float y = (float) ((a+b)/2);
        int res = 0;
        int speed_dir= 1;
        if (res == 1) {
            block = new Object(x, y, block_width, block_height, 0.006f*speed_dir, Colours.CYAN);
        } else {
            block = new Object(x, y, block_width, block_height, 0.0f, Colours.PURPLE);
        }
        blocks.addLast(block);  
        supposed_blocks.addLast(block);
    }

    private void initGame() {
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
            if(score>ScoreManager.Load()){
                ScoreManager.savebest_attamp(score);
            }
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

        // redButton.update(window);
        if (  inputManager.isKeyPressed(GLFW_KEY_SPACE)) {
            if(score>ScoreManager.Load()){
                ScoreManager.savebest_attamp(score);
            }
            blocks.clear();
            supposed_blocks.clear();
            // TextureCache.cleanup();
            cleanup();
            inputManager.cleanup();
            this.curState = State._menu_;
            return this.curState;
        }

        String label = String.valueOf(score);
        // this.text_score.update_text(label);
        return this.curState;
    }
    @Override
    public void render() {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        for (Object block : blocks) {
            block.draw();
        }
        // text_score.drawText();
        // redButton.draw();
        player.draw();
    }
    @Override
    public void cleanup() {
        blocks.clear();
        supposed_blocks.clear();
        // TextureCache.cleanup();
        player = null;
        block = null;
        // redButton = null;
        // text_score = null;
        if (vg != 0) {
            NanoVGGL3.nvgDelete(vg);
            vg = 0; // Обнуляем ссылку на контекст
        }
    }   
}