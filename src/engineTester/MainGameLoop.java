package engineTester;

import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;
import shaders.StaticShader;

public class MainGameLoop {

    public static void main(String[] args) {
        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

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

            shader.start();

            // Render the model
            renderer.render(model);

            shader.stop();

            DisplayManager.updateDisplay();
        }

        //Clean up after everything's done
        loader.cleanUp();
        shader.cleanUp();

        DisplayManager.closeDisplay();
    }
}
