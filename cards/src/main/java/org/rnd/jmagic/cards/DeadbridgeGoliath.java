package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Deadbridge Goliath")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("2GG")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class DeadbridgeGoliath extends Card
{
	public DeadbridgeGoliath(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Scavenge (4)(G)(G) ((4)(G)(G), Exile this card from your graveyard:
		// Put a number of +1/+1 counters equal to this card's power on target
		// creature. Scavenge only as a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Scavenge(state, "(4)(G)(G)"));
	}
}
