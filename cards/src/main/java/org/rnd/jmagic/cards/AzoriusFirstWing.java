package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Azorius First-Wing")
@Types({Type.CREATURE})
@SubTypes({SubType.GRIFFIN})
@ManaCost("WU")
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class AzoriusFirstWing extends Card
{
	public AzoriusFirstWing(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying, protection from enchantments
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.From(state, HasType.instance(Type.ENCHANTMENT), "enchantments"));
	}
}
