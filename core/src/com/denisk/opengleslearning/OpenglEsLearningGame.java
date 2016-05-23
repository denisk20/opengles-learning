package com.denisk.opengleslearning;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class OpenglEsLearningGame extends ApplicationAdapter {
	//Position attribute - (x, y)
	public static final int POSITION_COMPONENTS = 2;

	//Color attribute - (r, g, b, a)
	public static final int COLOR_COMPONENTS = 4;

	//Total number of components for all attributes
	public static final int NUM_COMPONENTS = POSITION_COMPONENTS + COLOR_COMPONENTS;

	//The "size" (total number of floats) for a single triangle
	public static final int PRIMITIVE_SIZE = 3 * NUM_COMPONENTS;

	//The maximum number of triangles our mesh will hold
	public static final int MAX_TRIS = 1;

	//The maximum number of vertices our mesh will hold
	public static final int MAX_VERTS = MAX_TRIS * 3;

//The array which holds all the data, interleaved like so:
//    x, y, r, g, b, a
//    x, y, r, g, b, a,
//    x, y, r, g, b, a,

 	final float[] verts = {
		// X, Y,
		// R, G, B, A
		0, 0,
		1.0f, 0.0f, 0.0f, 1.0f,

		100, 100f,
		1.0f, 0.0f, 0.0f, 1.0f,

		100f, 0,
		1.0f, 0.0f, 0.0f, 1.0f
	};

	private OrthographicCamera cam;
	private ShaderProgram shaderProgram;
	private Mesh mesh;

	@Override
	public void create () {
		mesh = new Mesh(true, MAX_VERTS, 0,
				new VertexAttribute(VertexAttributes.Usage.Position, POSITION_COMPONENTS, "a_position"),
				new VertexAttribute(VertexAttributes.Usage.ColorUnpacked, COLOR_COMPONENTS, "a_color"));
		mesh.setVertices(verts);
		Gdx.gl20.glDepthMask(false);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		cam = new OrthographicCamera();
		ShaderProgram.pedantic = true;
		shaderProgram = new ShaderProgram(
				Gdx.files.internal("shader.vert"),
				Gdx.files.internal("shader.frag")
		);
		if(! shaderProgram.isCompiled()) {
			throw new IllegalArgumentException("Couldn't compile shaderProgram");
		}
		String log = shaderProgram.getLog();
		if(log != null && log.length() > 0) {
			System.out.println("Log: " + log);
		}
	}

	@Override
	public void resize(int width, int height) {
		cam.setToOrtho(true, width, height);
	}

	@Override
	public void render () {

		cam.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		shaderProgram.begin();
		shaderProgram.setUniformMatrix("u_projTrans", cam.combined);
		mesh.render(shaderProgram, GL20.GL_TRIANGLES, 0, verts.length/NUM_COMPONENTS);
		shaderProgram.end();
	}

	@Override
	public void dispose() {
		mesh.dispose();
		shaderProgram.dispose();
	}
}
