
package com.idk.shit.ui;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColor3fv;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;



public class button {
    protected float screen_height=1000;
    protected float screen_width=650;

    private float x, y, width, height;
    private boolean isHovered = false;
    private boolean isClicked = false;
    private float[] color; 



    public button(float x, float y, float width, float height, String label,float[] color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;

    }

    public float left() {
        return x - width / 2;
    }
    
    public float right() {
        return x + width / 2;
    }
    
    public float bottom() {
        return y - height / 2;
    }
    
    public float top() {
        return y + height / 2;
    }
    public float x() {
        return x;
    }

    public float y() {
        return y;
    }
    public void draw() {
        glColor3fv(color); 
        glBegin(GL11.GL_QUADS);
            glVertex2f(x - width / 2, y - height / 2); 
            glVertex2f(x + width / 2, y - height / 2); 
            glVertex2f(x + width / 2, y + height / 2); 
            glVertex2f(x - width / 2, y + height / 2); 
        glEnd();
    }
    
   
        
    public void update(long window) {
        double[] mouseX = new double[1];
        double[] mouseY = new double[1];
        glfwGetCursorPos(window, mouseX, mouseY);
        
        float normX = (float) ((mouseX[0] / screen_width) * 2 - 1);  
        float normY = (float) (1 - (mouseY[0] / screen_height) * 2);  

        isHovered = (normX >= x - width/2  && normX <= x + width/2&&
                    normY >= y - height / 2 && normY <= y + height / 2);
        isClicked = isHovered && glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS;
    }

    public boolean isClicked() {
        return isClicked;
    }
   
}
