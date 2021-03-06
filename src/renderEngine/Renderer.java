package renderEngine;


import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/*
 * Render the model from the VAO
 */
public class Renderer {

    /*
     * Getting called once every frame and
     * prepared OpenGL to render the game
     */
    public void prepare() {
        // Clear the color from the previous frame
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        // Draw red background
        GL11.glClearColor(1,0,0,1);

    }

    /*
     * Render a RAW Model
     */
    public void render(RawModel model) {
        // Bind the VAO and give the ID of the VAO to bind
        GL30.glBindVertexArray(model.getVaoID());

        //Activate the attribute list in which the data is stored
        GL20.glEnableVertexAttribArray(0);

        // Render the model
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

        // After using it, disable attribute list
        GL20.glDisableVertexAttribArray(0);

        // Unbind VAO
        GL30.glBindVertexArray(0);
    }

}
