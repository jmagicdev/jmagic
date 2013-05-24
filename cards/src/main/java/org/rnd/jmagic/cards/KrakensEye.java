package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Kraken's Eye")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.DARKSTEEL, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class KrakensEye extends Card
{
	public static final class BlueLife extends org.rnd.jmagic.abilityTemplates.ColorTriggeredLife
	{
		public BlueLife(GameState state)
		{
			super(state, Color.BLUE);
		}
	}

	public KrakensEye(GameState state)
	{
		super(state);

		this.addAbility(new BlueLife(state));
	}
}
