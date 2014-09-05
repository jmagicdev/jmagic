package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Flight")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Starter2000.class, r = Rarity.COMMON), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = FifthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = FourthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.COMMON), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Flight extends Card
{
	public static final class FlightAbility1 extends StaticAbility
	{
		public FlightAbility1(GameState state)
		{
			super(state, "Enchanted creature has flying.");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Flying.class));
		}
	}

	public Flight(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature has flying.
		this.addAbility(new FlightAbility1(state));
	}
}
