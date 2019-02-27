package net.mgsx.dl3.model.components;

import net.mgsx.dl3.model.Component;
import net.mgsx.dl3.model.ComponentBehavior;
import net.mgsx.dl3.model.Electron;

public class Resistor extends ComponentBehavior
{
	@Override
	public void onElectronArrive(Component component, Electron electron) {
		
		// super.onElectronArrive(component, electron);
		
		if(component.energy + 1 <= component.energyMax){
			electron.energy *= .8f;
			component.addEnergy(1);
		}
	}
	
	@Override
	public void onElectronLeave(Component component, Electron electron) {
		super.onElectronLeave(component, electron);
	}
	
	@Override
	public int collect(Component component) {
		if(component.energy > 0){
			return 1;
		}
		return 0;
	}
}
