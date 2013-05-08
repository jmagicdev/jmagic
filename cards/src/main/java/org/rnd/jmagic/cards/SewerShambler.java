package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sewer Shambler")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class SewerShambler extends Card
{
	public SewerShambler(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Swampwalk (This creature is unblockable as long as defending player
		// controls a Swamp.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Swampwalk(state));

		// Scavenge (2)(B) ((2)(B), Exile this card from your graveyard: Put a
		// number of +1/+1 counters equal to this card's power on target
		// creature. Scavenge only as a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Scavenge(state, "(2)(B)"));
	}
}
