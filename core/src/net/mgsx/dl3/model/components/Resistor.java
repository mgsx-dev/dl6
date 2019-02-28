package net.mgsx.dl3.model.components;

import net.mgsx.dl3.model.Component;
import net.mgsx.dl3.model.ComponentBehavior;

public class Resistor extends ComponentBehavior
{
	
	@Override
	public int collect(Component component) {
		if(component.energy > 0){
			return 1;
		}
		return 0;
	}
}
