package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Carrion Call")
@Types({Type.INSTANT})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class CarrionCall extends Card
{
	public CarrionCall(GameState state)
	{
		super(state);

		// Put two 1/1 green Insect creature tokens with infect onto the
		// battlefield. (They deal damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		CreateTokensFactory factory = new CreateTokensFactory(2, 1, 1, "Put two 1/1 green Insect creature tokens with infect onto the battlefield.");
		factory.setColors(Color.GREEN);
		factory.setSubTypes(SubType.INSECT);
		factory.addAbility(org.rnd.jmagic.abilities.keywords.Infect.class);
		this.addEffect(factory.getEventFactory());
	}
}
