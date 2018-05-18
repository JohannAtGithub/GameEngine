package engineTester;

import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;

public class MainGameLoop {

    public static void main(String[] args) {
        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();

        // OpenGL expects vertices to be defined counter clockwise by default
        float[] vertices = {
                // Left bottom triangle
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                // Right top triangle
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
                -0.5f, 0.5f, 0f,
        };

        RawModel model = loader.loadToVAO(vertices);

        while(!Display.isCloseRequested()) {
            // Prepare the renerer every frame
            renderer.prepare();

            // Render the model
            renderer.render(model);

            DisplayManager.updateDisplay();
        }

        //Clean up the loader after everything's done
        loader.cleanUp();

        DisplayManager.closeDisplay();
    }
}
