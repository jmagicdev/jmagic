package org.rnd.jmagic.engine.patterns;

import org.rnd.jmagic.engine.*;

public class CastSpellWithNamePattern implements EventPattern
{
	private SetGenerator name;

	/**
	 * Creates an event pattern that matches casting a spell with the chosen
	 * name. This does the work of fussing out which half of a split card is
	 * being cast to support the Meddling-Mage-names-one-half functionality.
	 * 
	 * @param name A SetGenerator evaluating to a single String.
	 */
	public CastSpellWithNamePattern(SetGenerator name)
	{
		this.name = name;
	}

	@Override
	public boolean match(Event event, Identified object, GameState state)
	{
		if(event.type != EventType.CAST_SPELL_OR_ACTIVATE_ABILITY)
			return false;

		String name = this.name.evaluate(state, object).getOne(String.class);
		GameObject objectBeingCast = event.parameters.get(EventType.Parameter.OBJECT).evaluate(state, object).getOne(GameObject.class);
		Characteristics[] characteristics = objectBeingCast.getCharacteristics();
		java.util.Set<Integer> sidesBeingCast = event.parameters.get(EventType.Parameter.EFFECT).evaluate(state, object).getAll(Integer.class);
		return sidesBeingCast.stream().map(i -> characteristics[i].name.equals(name)).reduce((left, right) -> left || right).orElse(false);
	}

	@Override
	public boolean looksBackInTime()
	{
		return false;
	}

	@Override
	public boolean matchesManaAbilities()
	{
		return false;
	}
}
