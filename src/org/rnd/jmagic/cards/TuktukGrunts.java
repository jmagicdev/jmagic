package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Tuktuk Grunts")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.ALLY, SubType.WARRIOR})
@ManaCost("4R")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class TuktukGrunts extends Card
{
	public TuktukGrunts(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		this.addAbility(new org.rnd.jmagic.abilities.ZendikarAllyCounter(state, this.getName()));
	}
}
