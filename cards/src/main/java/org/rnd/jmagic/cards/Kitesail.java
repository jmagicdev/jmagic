package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Kitesail")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Worldwake.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class Kitesail extends Card
{
	public Kitesail(GameState state)
	{
		super(state);

		// Equipped creature gets +1/+0 and has flying.
		SetGenerator equippedCreature = EquippedBy.instance(This.instance());
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, equippedCreature, "Equipped creature", +1, +0, org.rnd.jmagic.abilities.keywords.Flying.class, false));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
