package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Igneous Pouncer")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("4BR")
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class IgneousPouncer extends Card
{
	public IgneousPouncer(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.TypeCycling.SwampCycling(state, "(2)"));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.TypeCycling.MountainCycling(state, "(2)"));
	}
}
