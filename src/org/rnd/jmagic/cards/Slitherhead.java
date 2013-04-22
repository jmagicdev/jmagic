package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Slitherhead")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.PLANT})
@ManaCost("(B/G)")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class Slitherhead extends Card
{
	public Slitherhead(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Scavenge (0) ((0), Exile this card from your graveyard: Put a number
		// of +1/+1 counters equal to this card's power on target creature.
		// Scavenge only as a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Scavenge(state, "(0)"));
	}
}
