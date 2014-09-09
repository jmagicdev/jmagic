package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Kathari Remnant")
@Types({Type.CREATURE})
@SubTypes({SubType.SKELETON, SubType.BIRD})
@ManaCost("2UB")
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class KathariRemnant extends Card
{
	public KathariRemnant(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new org.rnd.jmagic.abilities.Regenerate.Final(state, "(B)", "Kathari Remnant"));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cascade(state));
	}
}
