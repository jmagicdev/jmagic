package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Ajani's Sunstriker")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.CAT})
@ManaCost("WW")
@ColorIdentity({Color.WHITE})
public final class AjanisSunstriker extends Card
{
	public AjanisSunstriker(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Lifelink (Damage dealt by this creature also causes you to gain that
		// much life.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));
	}
}
