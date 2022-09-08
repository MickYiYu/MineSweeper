package com.yyu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class GameBoard {
    private int[][] board;
    private boolean firstClick = true;
    //Textures
    private Texture emptyTile;
    private Texture questionTile;
    private Texture bombTile;
    private Texture emptyFloor;
    private Texture bomb;
    private Texture oneTile,twoTile,threeTile,fourTile,fiveTile,sixTile,sevenTile,eightTile;

    private static final int Bomb = 9, EmptyTile = 10,FlaggedTile = 20, Nobombs=30;
    public GameBoard(){
        board = new int[16][30];
        initEmptyBoard();
        //load all textures
        emptyTile = new Texture("emptyTile.jpg");
        bomb = new Texture("bomb.jpg");
        oneTile = new Texture("oneTile.jpg");
        twoTile = new Texture("twoTile.jpg");
        threeTile = new Texture("threeTile.jpg");
        fourTile = new Texture("fourTile.jpg");
        fiveTile = new Texture("fiveTile.jpg");
        sixTile = new Texture("sixTile.jpg");
        sevenTile = new Texture("sevenTile.jpg");
        eightTile = new Texture("eightTile.jpg");
    }

    public boolean isValidLoc(int row, int col){
        return row >=0 && row < board.length && col>=0&&col<board[0].length;
    }

    public void handleCLick(int x, int y){
        int rowclicked = (y-10)/25;
        int colclicked = (x-10)/25;

        if(isValidLoc(rowclicked,colclicked)){
            board[rowclicked][colclicked] = board[rowclicked][colclicked]%10;
            if(firstClick){
                firstClick=false;
                placeBombs(rowclicked,colclicked);
            }
        }
    }

    private void placeBombs(int rowClicked, int colClicked){
        int bombCount = 0;
        while (bombCount<99){
            int randomRow = (int)(Math.random() * board.length);
            int randomCol = (int)(Math.random() * board[0].length);

            //if the random location is not equal to the first click
            if(randomRow != rowClicked && randomCol != colClicked){
                if(board[randomRow][randomCol]==EmptyTile){
                    board[randomRow][randomCol] = Bomb+10;
                    bombCount++;
                }
            }
        }
        System.out.println("Bombs placed: "+ bombCount);
    }

    public void draw(SpriteBatch spriteBatch) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] >= EmptyTile && board[row][col] < FlaggedTile) {
                    spriteBatch.draw(emptyTile,(10) + (col*25), (600-35) - (row * 25));
                }
                else if (board[row][col] == Bomb) {
                    spriteBatch.draw(emptyTile,(10) + (col*25), (600-35) - (row * 25));
                }
            }
        }
    }

    private void initEmptyBoard(){
        for(int i=0; i<board.length;i++){
            for (int j=0;j< board[i].length;j++){
                board[i][j] = 10;
            }
        }
    }

    private ArrayList<Location> getNeighbors(int row, int col) {
        ArrayList<Location> neighbors = new ArrayList<>();
        if (isValidLoc(row-1, col-1) == true) {
            neighbors.add(new Location(row - 1, col - 1));
        }
        else if (isValidLoc(row-1, col) == true) {
            neighbors.add(new Location(row - 1, col));
        }
        else if (isValidLoc(row-1, col+1) == true) {
            neighbors.add(new Location(row - 1, col + 1));
        }
        else if (isValidLoc(row, col-1) == true) {
            neighbors.add(new Location(row, col - 1));
        }
        else if (isValidLoc(row, col+1) == true) {
            neighbors.add(new Location(row, col + 1));
        }
        if (isValidLoc(row+1, col-1) == true) {
            neighbors.add(new Location(row + 1, col - 1));
        }
        if (isValidLoc(row+1, col+1) == true) {
            neighbors.add(new Location(row + 1, col + 1));
        }
        return neighbors;
    }

}
