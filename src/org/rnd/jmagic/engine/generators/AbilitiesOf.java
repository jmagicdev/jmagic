package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class AbilitiesOf extends SetGenerator
{
	public static AbilitiesOf instance(SetGenerator what)
	{
		return new AbilitiesOf(what);
	}

	private final SetGenerator what;

	private AbilitiesOf(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		Set evaluation = this.what.evaluate(state, thisObject);
		for(CanHaveAbilities object: evaluation.getAll(CanHaveAbilities.class))
		{
			ret.addAll(object.getNonStaticAbilities());
			ret.addAll(object.getKeywordAbilities());
			ret.addAll(object.getStaticAbilities());
		}

		for(GameObject object: state.stack().objects)
			if(object.isActivatedAbility() || object.isTriggeredAbility())
				if(evaluation.contains(((NonStaticAbility)object).getSource(state)))
					ret.add(object);

		return ret;
	}
}
