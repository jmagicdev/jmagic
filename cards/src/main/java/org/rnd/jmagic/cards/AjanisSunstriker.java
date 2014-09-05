package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Ajani's Sunstriker")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.CAT})
@ManaCost("WW")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON)})
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
