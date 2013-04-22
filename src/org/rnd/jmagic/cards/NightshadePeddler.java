package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Nightshade Peddler")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.DRUID})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class NightshadePeddler extends Card
{
	public NightshadePeddler(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Soulbond (You may pair this creature with another unpaired creature
		// when either enters the battlefield. They remain paired for as long as
		// you control both of them.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Soulbond(state));

		// As long as Nightshade Peddler is paired with another creature, both
		// creatures have deathtouch.
		this.addAbility(new org.rnd.jmagic.abilities.AbilityIfPaired.Final(state, "As long as Nightshade Peddler is paired with another creature, both creatures have deathtouch.", org.rnd.jmagic.abilities.keywords.Deathtouch.class));
	}
}
