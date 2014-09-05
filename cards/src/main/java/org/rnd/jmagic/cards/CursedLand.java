package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Cursed Land")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = FifthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = FourthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class CursedLand extends Card
{
	public static final class CurseLand extends EventTriggeredAbility
	{
		public CurseLand(GameState state)
		{
			super(state, "At the beginning of the upkeep of enchanted land's controller, Cursed Land deals 1 damage to that player.");

			SetGenerator controller = ControllerOf.instance(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS));

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, UpkeepStepOf.instance(controller));
			this.addPattern(pattern);

			this.addEffect(permanentDealDamage(1, controller, "Cursed Land deals 1 damage to that player."));
		}
	}

	public CursedLand(GameState state)
	{
		super(state);

		// Enchant land
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Land(state));

		// At the beginning of the upkeep of enchanted land's controller, Cursed
		// Land deals 1 damage to that player.
		this.addAbility(new CurseLand(state));
	}
}
