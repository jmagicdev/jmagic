package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Urban Burgeoning")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class UrbanBurgeoning extends Card
{
	public static final class UrbanBurgeoningAbility1 extends StaticAbility
	{
		public static final class UntapOtherUpkeeps extends StaticAbility
		{
			public UntapOtherUpkeeps(GameState state)
			{
				super(state, "Untap this land during each other player's untap step.");

				ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_UNTAP_EVENT);
				part.parameters.put(ContinuousEffectType.Parameter.EVENT, Identity.instance(untap(This.instance(), "Untap this land during each other player's untap step.")));
				this.addEffectPart(part);

				this.canApply = Both.instance(this.canApply, RelativeComplement.instance(OwnerOf.instance(CurrentStep.instance()), You.instance()));
			}
		}

		public UrbanBurgeoningAbility1(GameState state)
		{
			super(state, "Enchanted land has \"Untap this land during each other player's untap step.\"");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), UntapOtherUpkeeps.class));
		}
	}

	public UrbanBurgeoning(GameState state)
	{
		super(state);

		// Enchant land
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Land(state));

		// Enchanted land has
		// "Untap this land during each other player's untap step."
		this.addAbility(new UrbanBurgeoningAbility1(state));
	}
}
