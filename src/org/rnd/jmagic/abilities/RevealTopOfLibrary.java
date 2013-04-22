package org.rnd.jmagic.abilities;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class RevealTopOfLibrary extends StaticAbility
{
	public RevealTopOfLibrary(GameState state)
	{
		super(state, "Play with the top card of your library revealed.");

		SetGenerator library = LibraryOf.instance(You.instance());
		SetGenerator topCardOfLibrary = TopCards.instance(1, library);

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REVEAL);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, topCardOfLibrary);
		this.addEffectPart(part);
	}
}
