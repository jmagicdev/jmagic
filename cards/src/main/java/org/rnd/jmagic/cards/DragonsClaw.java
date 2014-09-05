package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Dragon's Claw")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2011.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Darksteel.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class DragonsClaw extends Card
{
	public static final class RedLife extends org.rnd.jmagic.abilityTemplates.ColorTriggeredLife
	{
		public RedLife(GameState state)
		{
			super(state, Color.RED);
		}
	}

	public DragonsClaw(GameState state)
	{
		super(state);

		this.addAbility(new RedLife(state));
	}
}
