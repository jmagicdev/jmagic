package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Flensermite")
@Types({Type.CREATURE})
@SubTypes({SubType.GREMLIN})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class Flensermite extends Card
{
	public Flensermite(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));

		// Lifelink (Damage dealt by this creature also causes you to gain that
		// much life.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));
	}
}
