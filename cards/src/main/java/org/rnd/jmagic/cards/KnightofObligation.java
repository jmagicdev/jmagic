package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Knight of Obligation")
@Types({Type.CREATURE})
@SubTypes({SubType.KNIGHT, SubType.HUMAN})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class KnightofObligation extends Card
{
	public KnightofObligation(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		// Extort (Whenever you cast a spell, you may pay (w/b). If you do, each
		// opponent loses 1 life and you gain that much life.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Extort(state));
	}
}
