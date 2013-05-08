package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wreath of Geists")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class WreathofGeists extends Card
{
	public static final class WreathofGeistsAbility1 extends StaticAbility
	{
		public WreathofGeistsAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +X/+X, where X is the number of creature cards in your graveyard.");

			SetGenerator X = Count.instance(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance()))));
			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), X, X));
		}
	}

	public WreathofGeists(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +X/+X, where X is the number of creature
		// cards in your graveyard.
		this.addAbility(new WreathofGeistsAbility1(state));
	}
}
