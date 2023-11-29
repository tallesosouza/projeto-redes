package com.terminalroot.game;

import java.io.IOException;
import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.terminalroot.game.models.StatusGameModel;
import com.terminalroot.game.socket.ClientPlayer;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img, tNave, tMissible, tEnemy;
	private Sprite nave, missible;
	private float posY, posX, xMissile, yMissile;
	private boolean attack,gameOver;
	private Array<Rectangle> enemies;
	private long lastEnemyTime;
	private int score, power;
	private FreeTypeFontGenerator generator;
	private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
	private BitmapFont bitmap;

	private ClientPlayer clientPlayer;

	@Override
	public void create () {
		this.clientPlayer = new ClientPlayer("127.0.0.1", 4000);
		this.startElements();
		try {
			this.start();
		} catch (Exception e ){
			e.printStackTrace();
		}

		batch = new SpriteBatch();
		img = new Texture("bg.png");
		tNave = new Texture("spaceship.png");
		tMissible = new Texture("missile.png");
		tEnemy = new Texture("enemy.png");

		nave = new Sprite(tNave);
		missible = new Sprite(tMissible);
		enemies = new Array<Rectangle>();
		
		posX = 0;
		posY = 0;
		xMissile = posX;
		yMissile = posY;
		lastEnemyTime = 0;
		score = 0;
		power = 3;
		attack = false;
		gameOver = false;
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 30;
		parameter.borderWidth = 1;
		parameter.borderColor = Color.BLACK;
		parameter.color = Color.WHITE;
		bitmap = generator.generateFont(parameter);
	}

	private void start() throws IOException {
		// final Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
		// clientSocket = new ClientSocket(socket);
		// System.out.println(
		// 		"Cliente conectado ao servidor no endereço " + SERVER_ADDRESS +
		// 		" e porta " + SERVER_PORT);

		// login();

		// new Thread(this).start();
	}
	
	private void startElements() {
		// this.allElements = new TodosElementos();
		// this.allElements.nave = new ElementoJogo();
		// this.allElements.missible = new ElementoJogo();
		// this.allElements.enemies = new ArrayList<>();
	}

	private void login() {
		System.out.print("Digite seu nome de usuário: ");
		// final String login = scanner.nextLine();
		// clientSocket.setUserName("Iniciar jogo");
		// clientSocket.sendMsg("Iniciar jogo");
	}

	public void updateScreen(String positionsInfor){
		
		try {

			ObjectMapper mapper = new ObjectMapper();
			// this.allElements = mapper.readValue(positionsInfor, TodosElementos.class);
			// this.posX =this.allElements.nave.X;
			// this.posY =this.allElements.nave.Y;

		} catch ( Exception e ){
			e.printStackTrace();
		}
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(img, 0, 0);

		// if(!this.awaitServer){
		// 	this.clientPlayer.checkKeyBoard();
		// 	// this.checkKeyboard();
		// 	this.moveMissile();
		// 	this.moveEnemies();
			
		// 	ScreenUtils.clear(1, 0, 0, 1);
		// 	if(!gameOver){
		// 		if(attack){
		// 			batch.draw(missible, xMissile, yMissile);
		// 		}
		// 		batch.draw(nave, posX, posY);
		
		// 		for(Rectangle enemy : enemies ){
		// 			batch.draw(tEnemy, enemy.x, enemy.y);
		// 		}
	
		// 		bitmap.draw(batch, "Score: "+ score, 20, Gdx.graphics.getHeight() - 10);
		// 		bitmap.draw(batch, "Life: "+ power, Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 10);
		// 	}	else {
		// 		bitmap.draw(batch, "Score: "+ score, 20, Gdx.graphics.getHeight() - 10);
		// 		bitmap.draw(batch, "GAME OVER", Gdx.graphics.getWidth() / 2 - 80, Gdx.graphics.getHeight() / 2);
	
		// 		if( Gdx.input.isKeyPressed(Input.Keys.ENTER)){
		// 			this.restartGame();
		// 		}
		// 	}
				
		if(this.clientPlayer.getAllElements().checkStatus(StatusGameEnum.IN_PROGRESS)){
			this.gameInProgress();
		} else if(this.clientPlayer.getAllElements().checkStatus(StatusGameEnum.CONNECTING)) {
			this.screenConnecting();
		} else if(this.clientPlayer.getAllElements().checkStatus(StatusGameEnum.WAITING)){
			this.waitPlayer();
		} else if (this.clientPlayer.getAllElements().checkStatus(StatusGameEnum.ERROR)){
			this.errorConnection();
		}
		batch.end();
	}

	private void screenConnecting() {
		bitmap.draw(batch, "AGUARDE!", Gdx.graphics.getWidth() / 2 - 70, Gdx.graphics.getHeight() / 2 + 30);
		bitmap.draw(batch, "ESTAMOS ESTABELECENDO CONEXÃO COM O SERVIDOR!", Gdx.graphics.getWidth() / 2 - 320, Gdx.graphics.getHeight() / 2 - 30);
	}

	private void errorConnection() {
		bitmap.draw(batch, "DESCULPE!", Gdx.graphics.getWidth() / 2 - 70, Gdx.graphics.getHeight() / 2 + 30);
		bitmap.draw(batch, "ERRO AO TENTAR SE CONECTAR AO SERVIDOR!", Gdx.graphics.getWidth() / 2 - 280, Gdx.graphics.getHeight() / 2 - 30);
		if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
			batch.dispose();
			img.dispose();
			tNave.dispose();
		}
	}

	private void waitPlayer() {
			this.clientPlayer.checkKeyBoard();
			bitmap.draw(batch, "INICIE O JOGO APERTANDO ENTER", Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2 + 30);
	}

	private void gameInProgress() {
		this.clientPlayer.checkKeyBoard();
		// batch.draw(this.clientPlayer.getAllElements().getTextureNave(), this.clientPlayer.getAllElements().getNave().getX(), this.clientPlayer.getAllElements().getNave().getY());
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		tNave.dispose();
		this.clientPlayer.closeConnection();
	}

	private void moveMissile() {
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && !attack){
			// clientSocket.sendMsg("SPACE");
			attack = true;
			yMissile = posY + nave.getHeight() / 2 - 12;
		}
		if(attack){
			if( xMissile < Gdx.graphics.getWidth() ){
				xMissile += 40;
			}else{
				xMissile = posX + nave.getWidth() / 2;
				attack = false;
			}
		} else {
			xMissile = posX + nave.getWidth() / 2;
			yMissile = posY + nave.getHeight() / 2 - 12;
		}
	}

	private void spawnEnemies() {
		Rectangle enemy =  new Rectangle( Gdx.graphics.getWidth(), MathUtils.random(0, Gdx.graphics.getHeight() - tEnemy.getHeight()), tEnemy.getWidth(), tEnemy.getHeight());
		enemies.add(enemy);
		lastEnemyTime = TimeUtils.nanoTime();
	}

	private void moveEnemies() {

		if(TimeUtils.nanoTime() - lastEnemyTime > 999999999 ){
			this.spawnEnemies();
		}

		for ( Iterator<Rectangle> iter = enemies.iterator(); iter.hasNext(); ){
			Rectangle enemy = iter.next();
			enemy.x -= 400 * Gdx.graphics.getDeltaTime();

			//Colisão com o missel
			if(collide(enemy.x, enemy.y, enemy.width, enemy.height, xMissile, yMissile, missible.getWidth(), missible.getHeight()) && attack){
				score += 1;
				iter.remove();
				attack = false;
				//Colisão com a nave
			}else if( collide(enemy.x, enemy.y, enemy.width, enemy.height, posX, posY, nave.getWidth(), nave.getHeight()) && !gameOver){
				power -= 1;
				if(power == 0){
					gameOver = true;
				}
				iter.remove();
			}

			if(enemy.x + tEnemy.getWidth() < 0) {
				iter.remove();
			}
		}
	}

	private void restartGame() {
		score = 0;
		power = 3;
		posX = 0;
		posY = 0;
		gameOver = false;
	}

	private boolean collide(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2){
		return x1 + w1 > x2 && x1 < x2 + w2 && y1 + h1 > y2 && y1 < y2 + h2;
	}
}
