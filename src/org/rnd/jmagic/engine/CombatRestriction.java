package org.rnd.jmagic.engine;

public class CombatRestriction
{
	private final SetGenerator restriction;
	private final int thisObjectID;
	private boolean fromDefender;

	public CombatRestriction(SetGenerator restriction, Identified thisObject)
	{
		this.restriction = restriction;
		this.thisObjectID = thisObject.ID;
		this.fromDefender = false;
	}

	void fromDefender()
	{
		this.fromDefender = true;
	}

	/**
	 * @return true of this restriction passes; false if it is violated
	 */
	public boolean check(GameState state)
	{
		Identified thisObject = state.get(this.thisObjectID);
		if(this.fromDefender && ((GameObject)thisObject).isAttacksWithDefender())
			return true;

		return this.restriction.evaluate(state, thisObject).isEmpty();
	}
}
