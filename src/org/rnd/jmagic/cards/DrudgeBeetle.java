package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Drudge Beetle")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class DrudgeBeetle extends Card
{
	public DrudgeBeetle(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Scavenge (5)(G) ((5)(G), Exile this card from your graveyard: Put a
		// number of +1/+1 counters equal to this card's power on target
		// creature. Scavenge only as a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Scavenge(state, "(5)(G)"));
	}
}
