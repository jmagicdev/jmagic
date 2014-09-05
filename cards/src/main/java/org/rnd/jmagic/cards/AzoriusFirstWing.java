package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Azorius First-Wing")
@Types({Type.CREATURE})
@SubTypes({SubType.GRIFFIN})
@ManaCost("WU")
@Printings({@Printings.Printed(ex = Dissension.class, r = Rarity.COMMON)})
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
