package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

public class ChosenFor extends SetGenerator
{
	public static ChosenFor instance(SetGenerator what)
	{
		return new ChosenFor(what);
	}

	private SetGenerator what;

	private ChosenFor(SetGenerator what)
	{
		this.what = what;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set ret = new Set();
		for(Linkable link: this.what.evaluate(state, thisObject).getAll(Linkable.class))
		{
			Identified i = (Identified)link;
			if(i.isActivatedAbility() || i.isTriggeredAbility())
				link = (((NonStaticAbility)i).getPrintedVersion(state));
			Set linkInformation = link.getLinkManager().getLinkInformation(state);
			if(linkInformation != null)
				ret.addAll(Identity.fromCollection(linkInformation).evaluate(state, null));
		}
		return ret;
	}

	@Override
	public java.util.Set<ManaSymbol.ManaType> extractColors(Game game, GameObject thisObject, java.util.Set<SetGenerator> ignoreThese) throws NoSuchMethodException
	{
		return Identity.fromCollection(this.evaluate(game, thisObject)).extractColors(game, thisObject, ignoreThese);
	}
}
