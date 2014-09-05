package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Wurm's Tooth")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2011.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Darksteel.class, r = Rarity.UNCOMMON)})
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
