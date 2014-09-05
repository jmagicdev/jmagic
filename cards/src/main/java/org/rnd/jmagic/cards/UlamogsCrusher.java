package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Ulamog's Crusher")
@Types({Type.CREATURE})
@SubTypes({SubType.ELDRAZI})
@ManaCost("8")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class UlamogsCrusher extends Card
{
	public UlamogsCrusher(GameState state)
	{
		super(state);

		this.setPower(8);
		this.setToughness(8);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Annihilator.Final(state, 2));

		// Ulamog's Crusher attacks each turn if able.
		this.addAbility(new org.rnd.jmagic.abilities.AttacksEachTurnIfAble(state, this.getName()));
	}
}
