package org.rnd.jmagic.engine.patterns;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public class ActivatedAbilitiesOfPattern implements SetPattern
{
	private boolean includeManaAbilities;
	private SetGenerator objects;

	public ActivatedAbilitiesOfPattern(SetGenerator objects)
	{
		this(objects, true);
	}

	public ActivatedAbilitiesOfPattern(SetGenerator objects, boolean includeManaAbilities)
	{
		this.objects = objects;
		this.includeManaAbilities = includeManaAbilities;
	}

	@Override
	public void freeze(GameState state, Identified thisObject)
	{
		this.objects = Identity.instance(this.objects.evaluate(state, thisObject)).noTextChanges();
	}

	@Override
	public boolean match(GameState state, Identified thisObject, Set set)
	{
		Set currentObjects = this.objects.evaluate(state, thisObject);
		for(ActivatedAbility a: set.getAll(ActivatedAbility.class))
			if(currentObjects.contains(a.getSource(state)) && (this.includeManaAbilities || !a.isManaAbility()))
				return true;
		return false;
	}
}
