// package com.idk.shit.ui;
// import java.io.File;
// import java.io.FileOutputStream;
// import java.io.InputStream;
// import java.nio.file.Files;
// import java.nio.file.Path;

// import org.lwjgl.nanovg.NVGColor;
// import org.lwjgl.nanovg.NanoVG;
// import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
// import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;
// import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
// import static org.lwjgl.nanovg.NanoVG.nvgFontFace;
// import static org.lwjgl.nanovg.NanoVG.nvgFontSize;
// import static org.lwjgl.nanovg.NanoVG.nvgText;
// import static org.lwjgl.nanovg.NanoVG.nvgTextBounds;

// import com.idk.shit.Main;

// public class TextRenderer {
//     protected float screen_height=800;
//     protected float screen_width=500;
//     private float x;
//     private float y;
//     private String label;
//     private float[] color; 
//     private long vg; 
//     private float max_height;
//     private float max_width;
//     private String fontName = "roboto"; // Имя шрифта


//     public TextRenderer(float x, float y, String label,float[] color,  long vg, float max_height,float  max_width ) {
//         this.x = (x+1)/2*screen_width;
//         this.y =(1-(y+1)/2) *screen_height;
//         this.label = label;
//         this.color = color;
//         this.vg=vg;
//         this.max_height=max_height/2*screen_height;
//         this.max_width=max_width/2*screen_width;
//         loadFont();
//     }
//     private void loadFont() {
//         InputStream fontStream = Main.class.getClassLoader().getResourceAsStream("fonts/Roboto_Condensed-MediumItalic.ttf");

//         if (fontStream != null) {
//             try {
//                 Path tempFile = Files.createTempFile("roboto", ".ttf");
//                 File fontFile = tempFile.toFile();

//                 try (FileOutputStream outputStream = new FileOutputStream(fontFile)) {
//                     byte[] buffer = new byte[1024];
//                     int bytesRead;
//                     while ((bytesRead = fontStream.read(buffer)) != -1) {
//                         outputStream.write(buffer, 0, bytesRead);
//                     }
//                 }

//                 String fontPath = fontFile.getAbsolutePath();

//                 int font = NanoVG.nvgCreateFont(vg, fontName, fontPath);
//                 if (font == -1) {
//                     System.err.println("Не удалось загрузить шрифт!");
//                 } 

//                 fontFile.deleteOnExit();
//             } catch (Exception e) {
//                 e.printStackTrace();
//             }
//         } else {
//             System.err.println("Шрифт не найден в ресурсах!");
//         }
//     }

//     private float  calc( float max_width,float  max_height){
//         float fontSize = 60.0f; 
//         float[] bounds = new float[4];

//         while (true) {
//             nvgFontSize(vg, fontSize);
//             nvgTextBounds(vg, 0, 0, this.label, bounds);

//             float textWidth = bounds[2] - bounds[0]; // Ширина текста
//             float textHeight = bounds[3] - bounds[1]; // Высота текста

//             if (textWidth <= max_width && textHeight <= max_height) {
//                 break; // Текст помещается в заданные размеры
//             }

//             fontSize -= 1.0f; // Уменьшаем размер шрифта

//             if (fontSize <= 0) {
//                 throw new RuntimeException("Не удалось подобрать размер шрифта для текста: " + label);
//             }
//         }
//         return fontSize;
//     }

//     public void update_text(String new_text){
//         this.label=new_text;
//         calc(this.max_width, this.max_height);
//     }

//     public void drawText() {
//         nvgBeginFrame(vg, screen_width, screen_height, 1); // Начинаем фрейм
//         float fontSize = calc( max_width, max_height);
//         nvgFontSize(vg, fontSize);
//          nvgFontFace(vg, fontName); // Используем загруженный шрифт

//         NVGColor color1 = NVGColor.create();
//         color1.r(0.0f);  // Красный
//         color1.g(0.0f);  // Зеленый
//         color1.b(0.0f);  // Синий
//         color1.a(1.0f);  // Альфа (непрозрачность)
//         nvgFillColor(vg, color1);

//         float[] bounds = new float[4];
//         nvgTextBounds(vg, 0, 0, label, bounds);
//         float textWidth = bounds[2] - bounds[0]; // Ширина текста
//         float textHeight = bounds[3] - bounds[1]; // Высота текста


//         nvgText(vg, this.x-textWidth/2, (int)(this.y+textHeight/3.5), label);
    
//         nvgEndFrame(vg);
//     }
//     // public void cleanup() {
//     //     NanoVG.nvgDelete(vg);
//     // }
// }