package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Wurm's Tooth")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.DARKSTEEL, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class WurmsTooth extends Card
{
	public static final class GreenLife extends org.rnd.jmagic.abilityTemplates.ColorTriggeredLife
	{
		public GreenLife(GameState state)
		{
			super(state, Color.GREEN);
		}
	}

	public WurmsTooth(GameState state)
	{
		super(state);

		this.addAbility(new GreenLife(state));
	}
}
