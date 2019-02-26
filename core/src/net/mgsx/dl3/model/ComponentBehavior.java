package net.mgsx.dl3.model;

public class ComponentBehavior {
	
	public void update(Component component, float delta){
		component.energy -= delta * 2f;
		if(component.energy < 0){
			component.energy = 0;
		}
	}

	public int getResistance() {
		return 10;
	}

}
