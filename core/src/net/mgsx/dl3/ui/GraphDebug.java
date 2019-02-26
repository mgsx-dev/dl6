package net.mgsx.dl3.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;

import net.mgsx.dl3.model.Card;
import net.mgsx.dl3.model.CardCell;

public class GraphDebug extends Group
{

	private BitmapFont font;
	private Array<Label> labels = new Array<Label>();
	private Card card;
	
	private static enum Mode{
		NONE, FLOW, DIRS
	}
	private Mode mode = Mode.NONE;

	public GraphDebug(Card card) {
		this.card = card;
		font = new BitmapFont();
		setTouchable(Touchable.disabled);
		
		LabelStyle style = new LabelStyle(font, Color.WHITE);
		for(int y=0 ; y<card.h ; y++){
			for(int x=0 ; x<card.w ; x++){
				Label label = new Label("", style);
				addActor(label);
				label.setPosition(x * 32 + 16, y * 32);
				labels.add(label);
			}
		}
	}
	
	@Override
	public void act(float delta) 
	{
		if(Gdx.input.isKeyJustPressed(Input.Keys.N)){
			mode = Mode.NONE;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.F)){
			mode = Mode.FLOW;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.D)){
			mode = Mode.DIRS;
		}
		for(int y=0 ; y<card.h ; y++){
			for(int x=0 ; x<card.w ; x++){
				Label label = labels.get(y*card.w+x);
				CardCell cell = card.cell(x,y);
				if(mode == Mode.NONE){
					label.setText("");
				}else if(mode == Mode.FLOW){
					if(cell.conductor){
						label.setText(String.valueOf(cell.flow));
					}else{
						label.setText("");
					}
				}else if(mode == Mode.DIRS){
					if(cell.conductor){
						label.setText(String.valueOf(cell.dirs));
					}else{
						label.setText("");
					}
				}
			}
		}
		super.act(delta);
	}

}
