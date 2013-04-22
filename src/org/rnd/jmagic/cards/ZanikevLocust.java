package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Zanikev Locust")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("5B")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class ZanikevLocust extends Card
{
	public ZanikevLocust(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Scavenge (2)(B)(B) ((2)(B)(B), Exile this card from your graveyard:
		// Put a number of +1/+1 counters equal to this card's power on target
		// creature. Scavenge only as a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Scavenge(state, "(2)(B)(B)"));
	}
}
