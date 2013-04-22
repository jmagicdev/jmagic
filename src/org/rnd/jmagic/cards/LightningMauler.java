package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Lightning Mauler")
@Types({Type.CREATURE})
@SubTypes({SubType.BERSERKER, SubType.HUMAN})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class LightningMauler extends Card
{
	public LightningMauler(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Soulbond (You may pair this creature with another unpaired creature
		// when either enters the battlefield. They remain paired for as long as
		// you control both of them.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Soulbond(state));

		// As long as Lightning Mauler is paired with another creature, both
		// creatures have haste.
		this.addAbility(new org.rnd.jmagic.abilities.AbilityIfPaired.Final(state, "As long as Lightning Mauler is paired with another creature, both creatures have haste.", org.rnd.jmagic.abilities.keywords.Haste.class));
	}
}
