package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sluiceway Scorpion")
@Types({Type.CREATURE})
@SubTypes({SubType.SCORPION})
@ManaCost("2BG")
@ColorIdentity({Color.BLACK, Color.GREEN})
public final class SluicewayScorpion extends Card
{
	public SluicewayScorpion(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Deathtouch (Any amount of damage this deals to a creature is enough
		// to destroy it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));

		// Scavenge (1)(B)(G) ((1)(B)(G), Exile this card from your graveyard:
		// Put a number of +1/+1 counters equal to this card's power on target
		// creature. Scavenge only as a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Scavenge(state, "(1)(B)(G)"));
	}
}
