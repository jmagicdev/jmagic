package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sanctum Plowbeast")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("4WU")
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class SanctumPlowbeast extends Card
{
	public SanctumPlowbeast(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(6);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.TypeCycling.PlainsCycling(state, "(2)"));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.TypeCycling.IslandCycling(state, "(2)"));
	}
}
