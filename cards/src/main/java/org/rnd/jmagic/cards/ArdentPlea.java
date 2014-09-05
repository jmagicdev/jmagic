package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Ardent Plea")
@Types({Type.ENCHANTMENT})
@ManaCost("1WU")
@Printings({@Printings.Printed(ex = AlaraReborn.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class ArdentPlea extends Card
{
	public ArdentPlea(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Exalted(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cascade(state));
	}
}
