package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Dragon's Claw")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.DARKSTEEL, r = Rarity.UNCOMMON)})
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
