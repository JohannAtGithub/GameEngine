package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;


public class DisplayManager {

    private static final int WIDTH = 1280, HEIGHT = 720, FPS_CAP = 120;

    //Erstellt das Display
    public static void createDisplay() {

        //Context Attribute definieren
        ContextAttribs attribs = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create(new PixelFormat(), attribs);
            Display.setTitle("Game Engine from scratch");
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        /*
         * Tell OpenGL where in the display
         * to render the game - we want it to
         * render fullscreen
         */
        GL11.glViewport(0,0, WIDTH, HEIGHT);
    }

    //Updated das Display bei jedem Frame
    public static void updateDisplay() {
        //Das Game nach einer festen FPS Zahl laufen lassen
        Display.sync(FPS_CAP);
        Display.update();
    }

    //Zerstört das Display
    public static void closeDisplay() {
        Display.destroy();
    }

}
