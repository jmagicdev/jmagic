package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Volition Reins")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("3UUU")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class VolitionReins extends Card
{
	public static final class VolitionReinsAbility1 extends EventTriggeredAbility
	{
		public VolitionReinsAbility1(GameState state)
		{
			super(state, "When Volition Reins enters the battlefield, if enchanted permanent is tapped, untap it.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator enchantedPermanent = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);
			this.interveningIf = Intersect.instance(enchantedPermanent, Tapped.instance());
			this.addEffect(untap(enchantedPermanent, "Untap it."));
		}
	}

	public static final class ControlChange extends StaticAbility
	{
		public ControlChange(GameState state)
		{
			super(state, "You control enchanted permanent.");

			SetGenerator enchantedPermanent = EnchantedBy.instance(This.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, enchantedPermanent);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			this.addEffectPart(part);
		}
	}

	public VolitionReins(GameState state)
	{
		super(state);

		// Enchant permanent
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Permanent(state));

		// When Volition Reins enters the battlefield, if enchanted permanent is
		// tapped, untap it.
		this.addAbility(new VolitionReinsAbility1(state));

		// You control enchanted permanent.
		this.addAbility(new ControlChange(state));
	}
}
