package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Horncaller's Chant")
@Types({Type.SORCERY})
@ManaCost("7G")
@ColorIdentity({Color.GREEN})
public final class HorncallersChant extends Card
{
	public HorncallersChant(GameState state)
	{
		super(state);

		// Put a 4/4 green Rhino creature token with trample onto the
		// battlefield,
		CreateTokensFactory factory = new CreateTokensFactory(1, 4, 4, "Put a 4/4 green Rhino creature token with trample onto the battlefield,");
		factory.setColors(Color.GREEN);
		factory.setSubTypes(SubType.RHINO);
		factory.addAbility(org.rnd.jmagic.abilities.keywords.Trample.class);
		this.addEffect(factory.getEventFactory());

		// then populate.
		this.addEffect(populate("then populate."));
	}
}
