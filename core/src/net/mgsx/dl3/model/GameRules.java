package net.mgsx.dl3.model;

import com.badlogic.gdx.utils.Array;

import net.mgsx.dl3.model.components.Capacitor;
import net.mgsx.dl3.model.components.Lcd;
import net.mgsx.dl3.model.components.Led;
import net.mgsx.dl3.model.components.Resistor;

public class GameRules {
	
	public static final int INITIAL_MONEY = 100;
	
	public static final int ELECTRON_ENERGY = 10;
	
	public static final float POWER_LOAD_SUPPORT = 100;
	
	public static final float POWER_COOL_DOWN = 10f;

	public static final int INITIAL_ELECTRONS = 100;
	
	
	private static Array<ComponentType> componentTypes;
	public static Array<ComponentType> getComponentTypes(){
		if(componentTypes == null){
			componentTypes = new Array<ComponentType>();
			
			componentTypes.add(new ComponentType("resistor", 128, new Resistor(), false, Dirs.LEFT | Dirs.RIGHT){{
				cost = 10;
				absorption = 2;
			}});
			componentTypes.add(new ComponentType("resistor", 160, new Resistor(), true, Dirs.UP | Dirs.DOWN){{
				cost = 10;
				absorption = 2;
			}});
			
			componentTypes.add(new ComponentType("capa", 320, new Capacitor(), false, Dirs.LEFT | Dirs.RIGHT){{
				cost = 20;
			}});
			
			componentTypes.add(new ComponentType("led", 192, new Led(), false, Dirs.LEFT, Dirs.RIGHT){{
				cost = 30;
			}});
			componentTypes.add(new ComponentType("led", 224, new Led(), true, Dirs.UP , Dirs.DOWN){{
				cost = 30;
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
