package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Confiscate")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("4UU")
@Printings({@Printings.Printed(ex = NinthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = UrzasSaga.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Confiscate extends Card
{
	// You control enchanted permanent.
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

	public Confiscate(GameState state)
	{
		super(state);

		// Enchant permanent (Target a permanent as you cast this. This card
		// enters the battlefield attached to that permanent.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Permanent(state));
		this.addAbility(new ControlChange(state));
	}
}
