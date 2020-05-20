package de.raffi.pluginlib.npc;

public enum Animation {
	
	SWING_ARM(0), HURT(1), STOP_SLEEP(2),EAT_FOOD(3),CRITICAL_EFFECT(4),MAGIC_CRITICAL_EFFECT(5);
	private int id;
	Animation(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
}
