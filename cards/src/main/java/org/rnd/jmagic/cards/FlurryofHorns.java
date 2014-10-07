package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Flurry of Horns")
@Types({Type.SORCERY})
@ManaCost("4R")
@ColorIdentity({Color.RED})
public final class FlurryofHorns extends Card
{
	public FlurryofHorns(GameState state)
	{
		super(state);

		// Put two 2/3 red Minotaur creature tokens with haste onto the
		// battlefield.
		CreateTokensFactory minotaurs = new CreateTokensFactory(2, 2, 3, "Put two 2/3 red Minotaur creature tokens with haste onto the battlefield.");
		minotaurs.setColors(Color.RED);
		minotaurs.setSubTypes(SubType.MINOTAUR);
		minotaurs.addAbility(org.rnd.jmagic.abilities.keywords.Haste.class);
		this.addEffect(minotaurs.getEventFactory());
	}
}
