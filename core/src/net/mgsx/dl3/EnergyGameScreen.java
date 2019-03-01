package net.mgsx.dl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import net.mgsx.dl3.assets.GameAssets;
import net.mgsx.dl3.events.PowerOnEvent;
import net.mgsx.dl3.events.ResignEvent;
import net.mgsx.dl3.factories.CardFactory;
import net.mgsx.dl3.model.Card;
import net.mgsx.dl3.model.CardCell;
import net.mgsx.dl3.model.CardWorld;
import net.mgsx.dl3.model.GameRules;
import net.mgsx.dl3.model.components.PowerSwitch;
import net.mgsx.dl3.ui.EnergyGameUI;
import net.mgsx.dl3.ui.GraphDebug;
import net.mgsx.dl3.utils.PixelPerfectViewport;
import net.mgsx.dl3.utils.StageScreen;

public class EnergyGameScreen extends StageScreen
{
	private OrthogonalTiledMapRenderer mapRenderer;
	private TiledMap map;
	private EnergyGameUI ui;
	private final Vector2 worldCursor = new Vector2();
	private int gx;
	private int gy;
	private ShapeRenderer shapes;
	private Card card;
	private CardWorld world;
	private boolean backToMenu;
	private Color bgColor = Color.valueOf("464646");
	
	public EnergyGameScreen(String cardID, String name) {
		super(new PixelPerfectViewport(EnergyGame.WIDTH, EnergyGame.HEIGHT));
		map = GameAssets.i.card(cardID);
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		shapes = new ShapeRenderer();
		card = CardFactory.fromMap(map);
		card.name = name;
		world = new CardWorld(card, map, stage);
		
		
		
		card.power.electronsRemain = GameRules.INITIAL_ELECTRONS;
		
		// stage
		stage.addActor(ui = new EnergyGameUI(card));
		ui.setFillParent(true);
		
		if(GameRules.DEBUG){
			stage.addActor(new GraphDebug(card));
		}
		
		card.updateFlows();
		
		ui.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(event instanceof ResignEvent){
					resign();
				}else if(event instanceof PowerOnEvent){
					powering();
				}
			}
		});
	}
	
	private void resign() {
		backToMenu = true;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(new InputMultiplexer(stage, new InputAdapter(){
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				CardCell cell = card.cell(gx, gy);
				if(cell.entity instanceof PowerSwitch){
					powering();
				}
				
				if(cell == null || !cell.conductor || cell.entity!=null) return true;
				if(ui.currentType != null){
					if(card.money < ui.currentType.cost) return true;
					card.money -= ui.currentType.cost;
					
					cell = card.createComponent(gx, gy, ui.currentType);
					CardFactory.setTile(map, cell);
					CardFactory.setAdjTiles(card, map, cell);
					cell.component.sprite = new Sprite(GameAssets.i.tile(cell.component.type.tileBaseID));
					cell.component.sprite.setPosition(cell.x*32, cell.y*32);
				}else if(cell.component != null){
					cell = card.removeComponent(gx, gy);
					CardFactory.setTile(map, cell);
					CardFactory.setAdjTiles(card, map, cell);
				}
				return true;
			}
		}));
	}
	
	private void powering(){
		PowerSwitch sw = card.powerSwitch;
		if(!sw.on){
			sw.on = true;
			card.power.enabled = true;
			CardFactory.setPowerSwitch(map, card.powerSwitchCell, sw.on);
		}
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(bgColor.r, bgColor.g, bgColor.b, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		viewport.unproject(worldCursor.set(Gdx.input.getX(), Gdx.input.getY()));
		
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
		gx = (int)(worldCursor.x / 32);
		gy = (int)(worldCursor.y / 32);
		
		
		boolean showCursor = !(card.finished || card.shortcut) && gx >= 2 && gy <= 8 && gy >= 1 && gx <= 16;
		
		gx = MathUtils.clamp(gx, 0, layer.getWidth()-1);
		gy = MathUtils.clamp(gy, 0, layer.getHeight()-1);

		
		ui.setCursorPosition(worldCursor);
		ui.setCursorVisible(showCursor);
		
		world.update(delta);
		
		mapRenderer.setView((OrthographicCamera) viewport.getCamera());
		mapRenderer.render();
		
		Batch batch = mapRenderer.getBatch();
		batch.begin();
		world.draw(batch);
		batch.end();
		
		super.render(delta);
		
		if(showCursor){
			shapes.setProjectionMatrix(viewport.getCamera().combined);
			shapes.begin(ShapeType.Line);
			shapes.rect(gx * 32, gy * 32, 32, 32);
			shapes.end();
		}
		
		if(backToMenu){
			dispose();
			EnergyGame.i().setScreen(new TitleScreen());
		}
	}
}
