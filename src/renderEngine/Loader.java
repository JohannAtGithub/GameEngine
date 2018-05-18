package renderEngine;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

/*
 * Loading 3D models into memory by storing
 * poitioning data about the model in an VAO
 */
public class Loader {

    /*
     * When we close the game, we want to
     * delete all the vaos that we created
     * in memeory. So keep track of all created
     * VAOs and VBOs that we created
     */
    private List<Integer> vaos = new ArrayList<Integer>();
    private List<Integer> vbos = new ArrayList<Integer>();

    /*
     * Taking positions of the models vertex,
     * load this data into VAO and then return
     * information about this VAO as a RAW Model
     * Object
     */
    public RawModel loadToVAO(float[] positions, int[] indices) {

        // store VAO ID in variable
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0, positions);
        unbindVAO();

        return new RawModel(vaoID, indices.length);
    }

    /*
     * When closing the game delete all VBOs and VAOs
     * in the game
     */
    public void cleanUp() {
        for (int vao : vaos) {
            GL30.glDeleteVertexArrays(vao);
        }

        for (int vbo : vbos) {
            GL15.glDeleteBuffers(vbo);
        }
    }

    /*
     * create a new empty VAO and return
     * the ID of the VAO
     */
    private int createVAO() {
        // Create the empty VAO and return the ID
        int vaoID = GL30.glGenVertexArrays();

        // Add VAO to the list to keep track of it
        vaos.add(vaoID);

        // Activate the VAO and bind it
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    /*
     * Store the data into one of the attribute list
     * of the VAO
     */
    private void storeDataInAttributeList(int attributeNumber, float[] data) {
        /*
         * Storing data into one of the attribute lists as a VBO
         */

        // Create an empty VBO
        int vboID = GL15.glGenBuffers();

        // Add VBO to the list to keep track of it
        vbos.add(vboID);

        // Bind the buffers to use it
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);

        // Convert our data into FloatBuffer by calling the written method
        FloatBuffer buffer = storeDataInFloatBuffer(data);

        // Store the buffer into the VBO
        // GL_STATIC_DRAW tells openGL that we never want to edit the data stored in the VBO
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

        // Put that VBO into the VAO in one of the attribute lists
        GL20.glVertexAttribPointer(attributeNumber, 3, GL11.GL_FLOAT, false, 0, 0);

        // We finished using the VBO - so unbind it
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

    }

    /*
     * unbinds the VAO after finishing
     * using the VAO
     */
    private void unbindVAO() {
        // Unbind the currently bound VAO
        GL30.glBindVertexArray(0);
    }

    /*
     * Load the indices buffer and bind it to a VAO
     */
    private void bindIndicesBuffer(int[] indices) {
        // Create an empty VBO
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);

        // Bind the buffer to use it
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);

        // Convert the indices in IntBuffer
        IntBuffer buffer = storeDataInIntBuffer(indices);

        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    /*
     * Store the indices into the VBO
     */
    private IntBuffer storeDataInIntBuffer(int[] data) {
        // Buffer to store into the VBO
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);

        //Make the buffer ready to be read from
        buffer.flip();
        return buffer;
    }

    /*
     * Store data into a vbo as a FloatBuffer
     */
    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        // Crate an empty FloatBuffer
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        // Put the data into the float buffer
        buffer.put(data);
        // Prepare the buffer to be read from
        buffer.flip();
        return buffer;
    }

}
