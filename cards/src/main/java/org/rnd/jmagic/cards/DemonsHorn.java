package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Demon's Horn")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2011.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Darksteel.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class DemonsHorn extends Card
{
	public static final class BlackLife extends org.rnd.jmagic.abilityTemplates.ColorTriggeredLife
	{
		public BlackLife(GameState state)
		{
			super(state, Color.BLACK);
		}
	}

	public DemonsHorn(GameState state)
	{
		super(state);

		this.addAbility(new BlackLife(state));
	}
}
