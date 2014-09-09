package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Nearheath Pilgrim")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.HUMAN})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class NearheathPilgrim extends Card
{
	public NearheathPilgrim(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Soulbond (You may pair this creature with another unpaired creature
		// when either enters the battlefield. They remain paired for as long as
		// you control both of them.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Soulbond(state));

		// As long as Nearheath Pilgrim is paired with another creature, both
		// creatures have lifelink.
		this.addAbility(new org.rnd.jmagic.abilities.AbilityIfPaired.Final(state, "As long as Nearheath Pilgrim is paired with another creature, both creatures have lifelink.", org.rnd.jmagic.abilities.keywords.Lifelink.class));
	}
}
