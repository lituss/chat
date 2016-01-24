package com.mygdx.chat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/*---------

posarem la linea de xat a sobre a la dreta i a la esquerra un boto de enviar
implementem les 2 funcions i techannnn



/------------*/


public class FrameChat implements Screen{
	private Stage stage;
	private Table table;
	private Skin skin;
	private Viewport viewport;
	private ChatClient chatClient;
	private Thread threadChatClient;
	private TextField alias;
	private TextArea linies;
	private List llistaUsuaris;
	private TextField texte;
	@Override
	public void show() {
		// TODO Auto-generated method stub
		skin = new Skin(Gdx.files.internal("uiskin.json"));	
		//viewport = new StretchViewport(400,800);
		//viewport.update(400 , 800, true ); 
		
		stage = new Stage (new StretchViewport(400,800));
		
		//stage.getCamera().update();
		table = new Table();
		table.debug();
		Label titol = new Label("chat",skin);
		titol.setPosition(180, 770);
		titol.setSize(46, 14);
		Label lAlias = new Label("Alias",skin);
		lAlias.setPosition(241,726);
		lAlias.setSize(46, 14);
		alias = new TextField("Test", skin);
		alias.setPosition(180, 700);
		alias.setSize(166,  20);
		linies = new TextArea("Linees de xat",skin);
		linies.setPosition(21,346);
		linies.setSize(277,  281);
		llistaUsuaris = new List(skin);
		llistaUsuaris.setPosition(298,346);
		llistaUsuaris.setSize(117,  281);
		
		TextButton conecta = new TextButton("Conecta",skin);
		conecta.setPosition(21,700);
		conecta.setSize(89,  23);
		conecta.addListener(new ChangeListener(){
			

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				conecta();
			}
		});
		
		stage.addActor(titol);
		stage.addActor(lAlias);
		stage.addActor(conecta);
		stage.addActor(alias);
		stage.addActor(linies);
		stage.addActor(llistaUsuaris);
		//SplitPane panel = new SplitPane(linies,llistaUsuaris,false,skin);
		texte = new TextField("missatges",skin);
		texte.setPosition(21, 318);
		texte.setSize(277,  20);
		texte.setTextFieldListener(new TextFieldListener(){
			

			

			@Override
			public void keyTyped(TextField textField, char c) {
				// TODO Auto-generated method stub
				if (c == '\n' || c=='\r'){
						System.out.println("S'ha pulsat <intro>");
						texte.getOnscreenKeyboard().show(false);
						chatClient.envia(texte.getText());
						texte.setText("");
						
					}
				System.out.printf("s'ha pulsat : %c\n",c);
			}
		});
		stage.addActor(texte);
		
		
		Array<String> newItems = new Array();
		newItems.add("Primer");
		newItems.add("Segon");
		newItems.add("Tercer");
		llistaUsuaris.setItems(newItems);
		//stage.getCamera().viewportWidth = Gdx.graphics.getWidth();
	    //stage.getCamera().viewportHeight = Gdx.graphics.getHeight();
		//stage.getViewport().setCamera(camera);
		/*
		table.setFillParent(true);
		table.add(titol).top();
		table.row();
		table.add(conecta).left();
		table.add(alias).left();
		table.row();
		table.add(linies).fillX().fillY().center();
		table.add(llistaUsuaris).fillX();
		table.row();
		table.add(texte).fillX();
		stage.addActor(table);
		*/
		//viewport.update(Gdx.graphics.getWidth() , Gdx.graphics.getHeight(), false );
		//stage.getCamera().translate(200,400, 0);
		//viewport = new FitViewport(400,800,stage.getCamera());
		//viewport.apply();
		//stage.getCamera().position.set(400,200,0);
		//stage.getCamera().update();
		//stage.getViewport().update(800, 400, true);
		//viewport.update(400, 800, false);
		//stage.getViewport().apply();
		//stage.getCamera().position.set(0,0,0);
		//stage.getCamera().update();
		//stage.getViewport().update(400,800,false);
		//stage.getViewport().apply();
		Gdx.input.setInputProcessor(stage);
		//System.out.printf("Camara ", stage.getCamera().)
		System.out.printf("mon real : %f %f \n",stage.getViewport().getWorldWidth(),stage.getViewport().getWorldHeight());
		System.out.printf("pantalla : %d %d ",stage.getViewport().getScreenWidth(),stage.getViewport().getScreenHeight());
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0,0,0,0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    stage.act(Gdx.graphics.getDeltaTime());
	 
	    stage.draw();
	    
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.getViewport().update(width, height, false);
		stage.getViewport().apply();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		dispose();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
		skin.dispose();
	}

	public void conecta(){
		chatClient = new ChatClient(this);
		new Thread(new Runnable(){
			@Override
			public void run(){
				new Thread(chatClient).start();
				/*final ChatClient chatClient = new ChatClient();
				Gdx.app.postRunnable(new Runnable(){
					@Override
					public void run(){
					setChatClient(chatClient);
					}
				});*/
			}
		}).start();
		//new Conecta(chatClient,this).start();
		
		linies.setText("");
	}
	public void setNames(String[] names) {
		// TODO Auto-generated method stub
		Array<String> newItems = new Array();
		
		for (String name : names )newItems.add(name);
		llistaUsuaris.setItems(newItems);
	}

	public void addMessage(String text) {
		// TODO Auto-generated method stub
		linies.appendText('\n'+text);
	}


	public String getName() {
		// TODO Auto-generated method stub
		return alias.getText();
	}
}
