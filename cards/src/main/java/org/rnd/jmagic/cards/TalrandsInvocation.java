package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Talrand's Invocation")
@Types({Type.SORCERY})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class TalrandsInvocation extends Card
{
	public TalrandsInvocation(GameState state)
	{
		super(state);

		// Put two 2/2 blue Drake creature tokens with flying onto the
		// battlefield.
		CreateTokensFactory factory = new CreateTokensFactory(2, 2, 2, "Put two 2/2 blue Drake creature tokens with flying onto the battlefield.");
		factory.setColors(Color.BLUE);
		factory.setSubTypes(SubType.DRAKE);
		factory.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
		this.addEffect(factory.getEventFactory());
	}
}
