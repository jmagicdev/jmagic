package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Stonewright")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SHAMAN})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class Stonewright extends Card
{
	public Stonewright(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Soulbond (You may pair this creature with another unpaired creature
		// when either enters the battlefield. They remain paired for as long as
		// you control both of them.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Soulbond(state));

		// As long as Stonewright is paired with another creature, each of those
		// creatures has "(R): This creature gets +1/+0 until end of turn."
		this.addAbility(new org.rnd.jmagic.abilities.AbilityIfPaired.Final(state, "As long as Stonewright is paired with another creature, each of those creatures has \"(R): This creature gets +1/+0 until end of turn.\"", org.rnd.jmagic.abilities.Firebreathing.class));
	}
}
