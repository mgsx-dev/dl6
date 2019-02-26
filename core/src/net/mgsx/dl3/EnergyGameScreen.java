package net.mgsx.dl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import net.mgsx.dl3.assets.GameAssets;
import net.mgsx.dl3.factories.CardFactory;
import net.mgsx.dl3.model.Card;
import net.mgsx.dl3.model.CardCell;
import net.mgsx.dl3.model.CardWorld;
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
	
	public EnergyGameScreen() {
		super(new PixelPerfectViewport(EnergyGame.WIDTH, EnergyGame.HEIGHT));
		map = GameAssets.i.card1;
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		stage.addActor(ui = new EnergyGameUI());
		ui.setFillParent(true);
		shapes = new ShapeRenderer();
		card = CardFactory.fromMap(map);
		world = new CardWorld(card);
		stage.addActor(new GraphDebug(card));
		
		card.updateFlows();
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(new InputMultiplexer(stage, new InputAdapter(){
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				CardCell cell = card.cell(gx, gy);
				if(cell == null || !cell.conductor || cell.entity!=null) return true;
				if(ui.currentType != null){
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
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		viewport.unproject(worldCursor.set(Gdx.input.getX(), Gdx.input.getY()));
		ui.setCursorPosition(worldCursor);
		
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
		gx = (int)(worldCursor.x / 32);
		gy = (int)(worldCursor.y / 32);
		gx = MathUtils.clamp(gx, 0, layer.getWidth()-1);
		gy = MathUtils.clamp(gy, 0, layer.getHeight()-1);
		Cell cell = layer.getCell(gx, gy);
		if(cell != null){
			
		}
		
		world.update(delta);
		
		mapRenderer.setView((OrthographicCamera) viewport.getCamera());
		mapRenderer.render();
		
		Batch batch = mapRenderer.getBatch();
		batch.begin();
		world.draw(batch);
		batch.end();
		
		super.render(delta);
		
		shapes.setProjectionMatrix(viewport.getCamera().combined);
		shapes.begin(ShapeType.Line);
		shapes.rect(gx * 32, gy * 32, 32, 32);
		shapes.end();
	}
}
