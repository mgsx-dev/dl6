package net.mgsx.dl3.model;

public class ComponentBehavior {
	
	public void update(Component component, float delta){
		component.energy -= delta * 2f;
		if(component.energy < 0){
			component.energy = 0;
		}
		if(component.energy >= component.energyMax/2){
			component.timeAtLimit += delta;
		}else{
			component.timeAtLimit = 0;
		}
		if(component.dead){
			component.sprite.setRegion(component.type.deadAnimation.getKeyFrame(component.animationTime, true));
		}
		else if(component.energy > 0){
			if(component.energy >= component.energyMax/2){
				component.sprite.setRegion(component.type.limitAnimation.getKeyFrame(component.animationTime, true));
			}else{
				component.sprite.setRegion(component.type.activeAnimation.getKeyFrame(component.animationTime, true));
			}
		}else{
			component.sprite.setRegion(component.type.idleAnimation.getKeyFrame(component.animationTime, true));
		}
	}

	public int getResistance() {
		return 10;
	}

	public void onElectronArrive(Component component, Electron electron) {
		electron.visible = false;
		
		if(component.energy + component.type.absorption <= component.energyMax){
			electron.energy -= component.type.absorption;
			component.addEnergy(component.type.absorption);
			
			if(component.energy >= component.energyMax/2){
				if(component.timeAtLimit > 5){
					component.dead = true;
					// TODO cut circuit ?
				}
			}
		}
	}

	public void onElectronLeave(Component component, Electron electron) {
		electron.visible = true;
	}

	public int collect(Component component) {
		return 0;
	}

}
