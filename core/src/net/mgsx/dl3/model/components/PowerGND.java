package net.mgsx.dl3.model.components;

import net.mgsx.dl3.model.Electron;
import net.mgsx.dl3.model.Entity;
import net.mgsx.dl3.model.GameRules;

public class PowerGND extends Entity{

	public float energy;
	
	public void onElectronArrive(Electron electron) 
	{
		energy += electron.energy;
	}
	
	@Override
	public void update(float delta) {
		energy  = Math.max(energy - delta * GameRules.POWER_COOL_DOWN, 0);
	}

}
