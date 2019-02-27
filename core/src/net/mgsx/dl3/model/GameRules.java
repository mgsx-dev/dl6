package net.mgsx.dl3.model;

import com.badlogic.gdx.utils.Array;

import net.mgsx.dl3.model.components.Capacitor;
import net.mgsx.dl3.model.components.Lcd;
import net.mgsx.dl3.model.components.Led;
import net.mgsx.dl3.model.components.Resistor;

public class GameRules {
	
	public static final int INITIAL_MONEY = 100;
	
	private static Array<ComponentType> componentTypes;
	public static Array<ComponentType> getComponentTypes(){
		if(componentTypes == null){
			componentTypes = new Array<ComponentType>();
			
			componentTypes.add(new ComponentType("resistor", 128, new Resistor(), false, Dirs.LEFT | Dirs.RIGHT){{
				cost = 10;
			}});
			componentTypes.add(new ComponentType("resistor", 160, new Resistor(), true, Dirs.UP | Dirs.DOWN){{
				cost = 10;
			}});
			
			componentTypes.add(new ComponentType("capa", 320, new Capacitor(), false, Dirs.LEFT | Dirs.RIGHT){{
				cost = 20;
			}});
			
			componentTypes.add(new ComponentType("led", 192, new Led(), false, Dirs.LEFT, Dirs.RIGHT){{
				cost = 40;
			}});
			componentTypes.add(new ComponentType("led", 224, new Led(), true, Dirs.UP , Dirs.DOWN){{
				cost = 40;
			}});
			
			componentTypes.add(new ComponentType("lcd", 256, new Lcd(), false, Dirs.LEFT | Dirs.RIGHT){{
				cost = 50;
			}});
			
			componentTypes.add(new ComponentType("lcd", 288, new Lcd(), true, Dirs.UP | Dirs.DOWN){{
				cost = 50;
			}});
			
		}
		return componentTypes;
	}
}
