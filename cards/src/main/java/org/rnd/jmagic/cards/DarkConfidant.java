package org.rnd.jmagic.cards;

import org.rnd.jmagic.abilities.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Dark Confidant")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = RavnicaCityOfGuilds.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class DarkConfidant extends Card
{
	public DarkConfidant(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// At the beginning of your upkeep, reveal the top card of your library
		// and put that card into your hand. You lose life equal to its
		// converted mana cost.
		this.addAbility(new DarkConfidantAbility(state));
	}
}
