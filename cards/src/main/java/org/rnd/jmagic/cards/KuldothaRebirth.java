package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kuldotha Rebirth")
@Types({Type.SORCERY})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class KuldothaRebirth extends Card
{
	public KuldothaRebirth(GameState state)
	{
		super(state);

		// As an additional cost to cast Kuldotha Rebirth, sacrifice an
		// artifact.
		this.addCost(sacrifice(You.instance(), 1, ArtifactPermanents.instance(), "sacrifice an artifact"));

		// Put three 1/1 red Goblin creature tokens onto the battlefield.
		CreateTokensFactory factory = new CreateTokensFactory(3, 1, 1, "Put three 1/1 red Goblin creature tokens onto the battlefield.");
		factory.setColors(Color.RED);
		factory.setSubTypes(SubType.GOBLIN);
		this.addEffect(factory.getEventFactory());
	}
}
