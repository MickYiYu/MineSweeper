package com.yyu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {

    private static final float WORLD_WIDTH = 800;
    private static final float WORLD_HEIGHT = 600;

    private static final int XGETTER = 10;
    private static final int YGETTER =35;

    GameBoard board = new GameBoard();
   private int mouseX;
   private int mouseY;

   BitmapFont tempFont = new BitmapFont();

    //Object that allows us to draw all our graphics
    private SpriteBatch spriteBatch;

    //Object that allwos us to draw shapes
    private ShapeRenderer shapeRenderer;

    //Camera to view our virtual world
    private Camera camera;

    //control how the camera views the world
    //zoom in/out? Keep everything scaled?
    private Viewport viewport;

    public void clearScreen() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void show() {
        camera = new OrthographicCamera(); //2D camera
        camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2,0);//move the camera
        camera.update();

        //freeze my view to 800x600, no matter the window size
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        spriteBatch = new SpriteBatch();

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true); //???, I just know that this was the solution to an annoying problem

        //load all textures
    }

    public void handleClick(){


        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            mouseY=Gdx.input.getY();
            mouseX=Gdx.input.getX();
            board.handleCLick(mouseX,mouseY);
        }

        if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)){
            mouseY=Gdx.input.getY();
            mouseX=Gdx.input.getX();
            board.handleRightClick(mouseX,mouseY);
        }
    }
    @Override
    public void render(float delta) {
        clearScreen();

        handleClick();//playerinput


        //all texture drawing must happen between begin() and end()
        spriteBatch.begin();
        board.draw(spriteBatch);
        tempFont.draw(spriteBatch,"Clicked at (" + mouseX + "," + mouseY+ ")",400,100);
        tempFont.draw(spriteBatch,"row: "+((mouseY-10)/25),400,120);
        tempFont.draw(spriteBatch,"col: "+(mouseX-10)/25,400,140);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
    }
}

