package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Etherium Abomination")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.HORROR})
@ManaCost("3UB")
@Printings({@Printings.Printed(ex = AlaraReborn.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class EtheriumAbomination extends Card
{
	public EtheriumAbomination(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Unearth(state, "(1)(U)(B)"));
	}
}
