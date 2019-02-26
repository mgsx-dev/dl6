package net.mgsx.dl3.model;

import com.badlogic.gdx.utils.Array;

import net.mgsx.dl3.model.components.Lcd;
import net.mgsx.dl3.model.components.Led;
import net.mgsx.dl3.model.components.Resistor;

public class GameRules {
	private static Array<ComponentType> componentTypes;
	public static Array<ComponentType> getComponentTypes(){
		if(componentTypes == null){
			componentTypes = new Array<ComponentType>();
			
			componentTypes.add(new ComponentType("resistor", 128, new Resistor(), false, Dirs.LEFT | Dirs.RIGHT));
			componentTypes.add(new ComponentType("led", 192, new Led(), false, Dirs.LEFT, Dirs.RIGHT));
			componentTypes.add(new ComponentType("lcd", 256, new Lcd(), false, Dirs.LEFT | Dirs.RIGHT));
			
			componentTypes.add(new ComponentType("resistor", 160, new Resistor(), true, Dirs.UP | Dirs.DOWN));
			componentTypes.add(new ComponentType("led", 224, new Led(), true, Dirs.UP , Dirs.DOWN));
			componentTypes.add(new ComponentType("lcd", 288, new Lcd(), true, Dirs.UP | Dirs.DOWN));
		}
		return componentTypes;
	}
}
