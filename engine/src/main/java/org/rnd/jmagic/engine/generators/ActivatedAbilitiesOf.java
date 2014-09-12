package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class ActivatedAbilitiesOf extends SetGenerator
{
	public static ActivatedAbilitiesOf instance(SetGenerator what)
	{
		return new ActivatedAbilitiesOf(what, false);
	}

	public static ActivatedAbilitiesOf instance(SetGenerator what, boolean everywhere)
	{
		return new ActivatedAbilitiesOf(what, everywhere);
	}

	private SetGenerator what;
	private boolean everywhere;

	private ActivatedAbilitiesOf(SetGenerator what, boolean everywhere)
	{
		this.what = what;
		this.everywhere = everywhere;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set sources = this.what.evaluate(state, thisObject);
		if(this.everywhere)
			return java.util.stream.StreamSupport.stream(state.getAll(ActivatedAbility.class).spliterator(), false) //
			.filter(o -> sources.contains(o.getSource(state))) //
			.collect(java.util.stream.Collectors.toCollection(Set::new));

		Set ret = new Set();
		for(GameObject object: sources.getAll(GameObject.class))
			for(NonStaticAbility ability: object.getNonStaticAbilities())
				if(ability.isActivatedAbility())
					ret.add(ability);

		return ret;
	}
}
