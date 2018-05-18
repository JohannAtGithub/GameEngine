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

        // OpenGL expects vertices to be defined counter clockwise for each triangle by default
        float[] vertices = {
                -0.5f, 0.5f, 0,  //V0
                -0.5f, -0.5f, 0, //V1
                0.5f, -0.5f, 0,  //V2
                0.5f, 0.5f, 0    //V3
        };

        int[] indices = {
                0,1,3, // Top left triangle
                3,1,2 //Bottom right triangle
        };

        RawModel model = loader.loadToVAO(vertices, indices);

        while(!Display.isCloseRequested()) {
            // Prepare the renderer every frame
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
