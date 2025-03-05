package com.idk.shit.game;

import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_M;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL11;

import com.idk.shit.graphics.TextureCache;
import com.idk.shit.objects.Object;
import com.idk.shit.objects.Player;
import com.idk.shit.ui.TextRenderer;
import com.idk.shit.ui.button;
import com.idk.shit.utils.Colours;
import com.idk.shit.utils.ScoreManager;
import com.idk.shit.utils.rand;

public class Game extends GameState {
    private Deque<Object> blocks = new ArrayDeque<>();
    private Deque<Object> supposed_blocks = new ArrayDeque<>();

    private float block_height = 0.04f;
    private float block_width = 0.35f;

    long vg = NanoVGGL3.nvgCreate(NanoVG.NVG_ALIGN_BASELINE);
    protected String [] textures = new String[]{"src\\main\\resources\\textures\\photo_2025-03-03_11-41-26.jpg.png", "src\\main\\resources\\textures\\photo_2025-03-03_11-41-26.jpg.png"};
    private String path_to_block_texture="src\\main\\resources\\textures\\photo_2025-03-03_11-41-26.jpg.png";
    private String path_to_player_texture="src\\main\\resources\\textures\\pngegg.png";

    private Player player = new Player(0.0f, 0.0f, 0.2f, 0.13f, 0.02f,Colours.WHITE, path_to_player_texture );
    private Object block = new Object(0.0f, -0.5f, block_width, block_height, 0.0f, Colours.PURPLE, path_to_block_texture);
    private button redButton = new button(-0.7f, 0.95f, 0.6f, 0.1f, "menu", Colours.GREEN, vg);
    
    private  TextRenderer text_score = new TextRenderer(0.8f, 0.8f, "", Colours.BLACK, vg, 0.2f,0.4f );

    private boolean go_left = false;
    private boolean go_right = false;
    private boolean go_to_menu = false;

    private float speed_player_x = 0.05f;

    private float max_speed_y = 0.06f;
    private float accel_y = -0.003f;

    private int score = 0;

    private float max_height = -max_speed_y * max_speed_y / (2 * accel_y) - 0.05f;

    private long window;
    private StateManager stateManager;

    public Game(long window, StateManager stateManager) {
        super(window, stateManager); // Передаем window в родительский класс
        this.window = window; // Сохраняем окно
        this.stateManager = stateManager;
        initGame();
        String label = String.valueOf(score);
        this.text_score = new TextRenderer(0.8f, 0.8f, label, Colours.BLACK, vg, 0.2f, 0.4f);
        for (String texturePath : textures) {
            TextureCache.getTexture(texturePath); // здесь структуры загружаются через TExtureCache в Map
        }
    }

    public void AddBlock( float left, float right, float b, float a){
        rand randomizer = new rand();  
        int texture_number= (int)(Math.random() * (textures.length- 0) + 0);// используем рандом чтобы получить случайную текстуру блока
        path_to_block_texture=textures[texture_number];
        float x = randomizer.rand_x(left, right); 
        float y = (float) (Math.random() * (b - a) + a);
        int res = randomizer.rand(new int[]{1, 2}, new int[]{score, 100});
        int speed_dir= randomizer.rand(new int[]{-1, 1}, new int[]{1, 1});
        if (res == 1) {
            block = new Object(x, y, block_width, block_height, 0.006f*speed_dir, Colours.CYAN, path_to_block_texture);
        } else {
            block = new Object(x, y, block_width, block_height, 0.0f, Colours.PURPLE, path_to_block_texture);
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

        // Устанавливаем обработчик клавиатуры
        glfwSetKeyCallback(this.window, null); // Очистка предыдущего обработчика
       WeakReference<Game> weakGame = new WeakReference<>(this);
            glfwSetKeyCallback(window, (wind, key, scancode, action, mods) -> {
                Game game = weakGame.get();
                if (game != null) {
                    if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                        glfwSetWindowShouldClose(game.window, true);
                    if (key == GLFW_KEY_LEFT) {
                        game.go_left = action != GLFW_RELEASE;
                    }
                    if (key == GLFW_KEY_RIGHT) {
                        game.go_right = action != GLFW_RELEASE;
                    }
                    if (key == GLFW_KEY_M) {
                        game.go_to_menu = true;
                    }
                }
            });
    }
    
    @Override
    public void update() {
        if (player.fall_down()==true){
            if(score>ScoreManager.Load()){
                ScoreManager.savebest_attamp(score);
            }
            cleanup();
            stateManager.setState(new GameOver(window, stateManager, score));
            return;
        }
        if (go_left) {
            player.update_object(-speed_player_x);
        } else if (go_right) {
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

        redButton.update(window);
        if ( redButton.isClicked() || go_to_menu==true) {
            go_to_menu=false;
            if(score>ScoreManager.Load()){
                ScoreManager.savebest_attamp(score);
            }
            blocks.clear();
            supposed_blocks.clear();
            TextureCache.cleanup();
            stateManager.setState(new Menu(window, stateManager));
            cleanup();
            return;
        }

        String label = String.valueOf(score);
        this.text_score.update_text(label);
    }
    @Override
    public void render() {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        for (Object block : blocks) {
            block.draw();
        }
        text_score.drawText();
        redButton.draw();
        player.draw();
    }
    @Override
    public void cleanup() {
        glfwSetKeyCallback(window, null); // Удаляем обработчик
        blocks.clear();
        supposed_blocks.clear();
        TextureCache.cleanup();
        player = null;
        block = null;
        redButton = null;
        text_score = null;
        if (vg != 0) {
            NanoVGGL3.nvgDelete(vg);
            vg = 0; // Обнуляем ссылку на контекст
        }
    }   
}