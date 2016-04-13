package com.denisk.opengleslearning;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class OpenglEsLearningGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	final float[] triangle1VerticesData = {
		// X, Y, Z,
		// R, G, B, A
		-0.5f, -0.25f, 0.0f,
		1.0f, 0.0f, 0.0f, 1.0f,

		0.5f, -0.25f, 0.0f,
		0.0f, 0.0f, 1.0f, 1.0f,

		0.0f, 0.559016994f, 0.0f,
		0.0f, 1.0f, 0.0f, 1.0f};



	/** How many bytes per float. */
	private final int mBytesPerFloat = 4;
	/** Store our model data in a float buffer. */
	private FloatBuffer mTriangle1Vertices;
	private FloatBuffer mTriangle2Vertices;
	private FloatBuffer mTriangle3Vertices;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		mTriangle1Vertices = ByteBuffer.allocateDirect(triangle1VerticesData.length * mBytesPerFloat)
			.order(ByteOrder.nativeOrder()).asFloatBuffer();
		mTriangle1Vertices.put(triangle1VerticesData).position(0);
	}


	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
}
