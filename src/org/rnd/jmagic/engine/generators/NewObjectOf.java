package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to the new object in any ZoneChange
 */
public class NewObjectOf extends SetGenerator
{
	public static NewObjectOf instance(SetGenerator zoneChanges)
	{
		return new NewObjectOf(zoneChanges);
	}

	/**
	 * This is marked as deprecated to avoid a programming mistake because
	 * {@link ChosenFor} (under the hood) automatically translates
	 * {@link ZoneChange} into the new object of the {@link ZoneChange}
	 */
	@Deprecated
	public static NewObjectOf instance(ChosenFor chosenFor)
	{
		return new NewObjectOf(chosenFor);
	}

	private final SetGenerator zoneChanges;

	private NewObjectOf(SetGenerator zoneChanges)
	{
		this.zoneChanges = zoneChanges;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(ZoneChange change: this.zoneChanges.evaluate(state, thisObject).getAll(ZoneChange.class))
			ret.add(state.get(change.newObjectID));
		return ret;
	}
}
