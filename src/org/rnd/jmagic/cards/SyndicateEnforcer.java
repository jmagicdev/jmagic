package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Syndicate Enforcer")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ROGUE})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class SyndicateEnforcer extends Card
{
	public SyndicateEnforcer(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Extort (Whenever you cast a spell, you may pay (w/b). If you do, each
		// opponent loses 1 life and you gain that much life.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Extort(state));
	}
}
