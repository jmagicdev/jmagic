package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Dying Wish")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class DyingWish extends Card
{
	public static final class DyingWishAbility1 extends EventTriggeredAbility
	{
		public DyingWishAbility1(GameState state)
		{
			super(state, "When enchanted creature dies, target player loses X life and you gain X life, where X is its power.");

			SetGenerator enchanted = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);
			this.addPattern(whenXDies(enchanted));

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			SetGenerator X = PowerOf.instance(OldObjectOf.instance(TriggerZoneChange.instance(This.instance())));

			this.addEffect(loseLife(target, X, "Target player loses X life"));
			this.addEffect(gainLife(You.instance(), X, "and you gain X life, where X is its power."));
		}
	}

	public DyingWish(GameState state)
	{
		super(state);

		// Enchant creature you control
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.CreatureYouControl(state));

		// When enchanted creature dies, target player loses X life and you gain
		// X life, where X is its power.
		this.addAbility(new DyingWishAbility1(state));
	}
}
