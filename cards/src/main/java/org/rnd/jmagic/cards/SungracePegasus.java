package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Sungrace Pegasus")
@Types({Type.CREATURE})
@SubTypes({SubType.PEGASUS})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class SungracePegasus extends Card
{
	public SungracePegasus(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Flying (This creature can't be blocked except by creatures with
		// flying or reach.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Lifelink (Damage dealt by this creature also causes you to gain that
		// much life.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));
	}
}
