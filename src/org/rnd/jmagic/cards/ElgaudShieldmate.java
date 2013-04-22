package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Elgaud Shieldmate")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class ElgaudShieldmate extends Card
{
	public ElgaudShieldmate(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Soulbond (You may pair this creature with another unpaired creature
		// when either enters the battlefield. They remain paired for as long as
		// you control both of them.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Soulbond(state));

		// As long as Elgaud Shieldmate is paired with another creature, both
		// creatures have hexproof. (They can't be the targets of spells or
		// abilities your opponents control.)
		this.addAbility(new org.rnd.jmagic.abilities.AbilityIfPaired.Final(state, "As long as Elgaud Shieldmate is paired with another creature, both creatures have hexproof.", org.rnd.jmagic.abilities.keywords.Hexproof.class));
	}
}
