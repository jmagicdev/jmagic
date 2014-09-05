package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Angel's Feather")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2011.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Darksteel.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class AngelsFeather extends Card
{
	public static final class WhiteLife extends org.rnd.jmagic.abilityTemplates.ColorTriggeredLife
	{
		public WhiteLife(GameState state)
		{
			super(state, Color.WHITE);
		}
	}

	public AngelsFeather(GameState state)
	{
		super(state);

		this.addAbility(new WhiteLife(state));
	}
}
