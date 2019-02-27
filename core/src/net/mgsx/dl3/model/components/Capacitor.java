package net.mgsx.dl3.model.components;

import net.mgsx.dl3.model.Component;
import net.mgsx.dl3.model.ComponentBehavior;
import net.mgsx.dl3.model.Electron;

public class Capacitor extends ComponentBehavior {

	@Override
	public void onElectronArrive(Component component, Electron electron) {
		super.onElectronArrive(component, electron);
		if(component.energy < component.energyMax / 2){
			electron.speed = Math.max(electron.speed / 2, 1);
		}
	}
	
	@Override
	public void onElectronLeave(Component component, Electron electron) {
		super.onElectronLeave(component, electron);
	}
}
