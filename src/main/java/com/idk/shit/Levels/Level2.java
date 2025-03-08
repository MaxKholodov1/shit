// package com.idk.shit.Levels;

// import java.util.ArrayDeque;
// import java.util.Deque;
// import java.util.Iterator;

// import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
// import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
// import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
// import com.idk.shit.graphics.Texture;
// import com.idk.shit.graphics.Shader;

// import com.idk.shit.objects.Object;
// import com.idk.shit.objects.Player;
// import com.idk.shit.ui.button;
// import com.idk.shit.utils.Colours;
// import com.idk.shit.utils.InputManager;
// import com.idk.shit.utils.ScoreManager;
// import com.idk.shit.utils.rand;
// import com.idk.shit.game.GameState;
// import com.idk.shit.game.StateManager;
// import com.idk.shit.game.GameOver;
// import com.idk.shit.game.Menu;


// public class Level2 extends GameState {
//     private Deque<Object> blocks = new ArrayDeque<>();//дек с видимыми блоками
//     private Deque<Object> supposed_blocks = new ArrayDeque<>();// дек с видимыми блокамии "взорванными"

//     private float block_height = 0.045f;
//     private float block_width = 0.25f;
//     private Player player;
//     private Object block;
//     private float speed_player_x = 0.03f;
//     private float max_speed_y = 0.07f;
//     private float accel_y = -0.003f;
//     private int score = 0;
//     private Shader shader;
//     public float screen_height=1000;
//     public float screen_width=650;
//     public float RATIO = (float)screen_width/(float)screen_height;

//     private float max_height = -max_speed_y * max_speed_y / (2 * accel_y) - 0.05f;
//     private button MenuButton = new button(-0.7f, 0.95f, 0.6f, 0.1f, "menu", Colours.GREEN);



//     public Level2(long window,InputManager inputManager,StateManager stateManager, Texture blockTexture, Texture playerTexture) {
//         super( window, inputManager, stateManager, blockTexture, playerTexture ); 
//         InitLevel2();
      
//     }public void AddBlock( float left, float right, float b, float a){
        
//         rand randomizer = new rand();  
//         float x = randomizer.rand_x(left, right); 
//         float y = (float) (Math.random() * (b - a) + a);
//         int res = randomizer.rand(new int[]{1, 2}, new int[]{score, 100});
//         int speed_dir= randomizer.rand(new int[]{-1, 1}, new int[]{1, 1});
//         if (res == 1) {
//             block = new Object(x, y, block_width, block_height, 0.006f*speed_dir, Colours.CYAN, this.blockTexture);
//         } else {
//             block = new Object(x, y, block_width, block_height, 0.0f, Colours.PURPLE, this.blockTexture);
//         }
//         blocks.addLast(block);  
//         supposed_blocks.addLast(block);
//     }

//     @Override
//     public void cleanup() {
//         blocks.clear();
//         supposed_blocks.clear();
//         player = null;
//         block = null;
//         inputManager.cleanup();
//         // redButton = null;
//     }   

//     private void InitLevel2() {
//         player = new Player(0.0f, 0.0f, 0.15f, 0.17f, 0.02f,Colours.WHITE, this.playerTexture, shader);
//         block = new Object(0.0f, -0.5f, block_width, block_height, 0.0f, Colours.PURPLE, this.blockTexture, shader );
//         float left =  block_width / 2  - RATIO;
//         float right = - block_width / 2 + RATIO;
//         AddBlock(0f, 0.001f, -0.4f, -0.5f);
//         float prev_height;
//         for (int i = 0; i < 6; ++i) {
//             prev_height = supposed_blocks.getLast().getTop();
//             float a = prev_height + max_height / 2;
//             float b = prev_height + max_height;
//             AddBlock(left, right, b, a);

//         }
//     }
//     @Override
//     public void update(){
//         if (player.fall_down()==true){
//             cleanup();
//             stateManager.setState(new GameOver(window, inputManager, stateManager, blockTexture, playerTexture));
//             return;
//         }
//         MenuButton.update(this.window);
//         if (MenuButton.isClicked()==true  || inputManager.isKeyPressed(GLFW_KEY_SPACE)){
//             cleanup();
//             stateManager.setState(new Menu(window, inputManager, stateManager, blockTexture, playerTexture));
//             return;
//         }

//         if (inputManager.isKeyPressed(GLFW_KEY_LEFT) && !inputManager.isKeyPressed(GLFW_KEY_RIGHT) ) {
//             player.update_object(-speed_player_x);
//         } else if (inputManager.isKeyPressed(GLFW_KEY_RIGHT) && !inputManager.isKeyPressed(GLFW_KEY_LEFT)) {
//             player.update_object(speed_player_x);
//         } else {
//             player.update_object(0);// обновление скорости игрока
//         }
        
//         for (Object block : supposed_blocks) {
//             float block_speed_x = block.getSpeed_x();// обновление скорости блоков
//             block.update_object(block_speed_x);
//         }


//         if (player.getY() > 0) {
//             for (Object block : supposed_blocks) {
//                 block.SetY(block.getY()-player.getY());// смещение всех обьектов вниз если игрок выше середины экрана
//             }
//             player.SetY(0f);
//         }

//         for (Iterator<Object> iterator = blocks.iterator(); iterator.hasNext(); ) {// если обьект ниже  экрана удаляем его 
//             Object block = iterator.next();
//             if (block.getY() < -1) {
//                 iterator.remove(); 
//             }
//         }

//         int cnt = 0;
//         for (Iterator<Object> iterator = supposed_blocks.iterator(); iterator.hasNext(); ) {
//             Object block = iterator.next();
//             if (block.getY() < -1) {// если обьект ниже  экрана удаляем его
//                 cnt++;
//                 score++;
//                 iterator.remove(); 
//             }
//         }

//         float prev_height;// высота предыдущего блока чтобы генерировать новый 
//         for (int i = 0; i < cnt; ++i) {// генерация новых блоков по количеству столько сколько и было удалено
//             prev_height = supposed_blocks.getLast().getY();
//             float a = prev_height + max_height / 2;
//             float b = prev_height + max_height;
//             float left =  block_width / 2 - RATIO;
//             float right = - block_width / 2 + RATIO;
//             AddBlock(left, right, b, a);
//         }

//         for (Object block : blocks) {
//             block.collision(player);// проверяем столкновение и меняем скорости при столкновении
//         }
//     }
    
//     @Override
//     public void render() {
//         for (Object block : blocks) {
//             block.draw();
//         }
//         MenuButton.draw();
//         player.draw();
//     }
// }