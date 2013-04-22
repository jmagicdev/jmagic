package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Annex")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Annex extends Card
{
	public static final class AnnexAbility1 extends StaticAbility
	{
		public AnnexAbility1(GameState state)
		{
			super(state, "You control enchanted land.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, EnchantedBy.instance(This.instance()));
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());

			this.addEffectPart(part);
		}
	}

	public Annex(GameState state)
	{
		super(state);

		// Enchant land (Target a land as you cast this. This card enters the
		// battlefield attached to that land.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Land(state));

		// You control enchanted land.
		this.addAbility(new AnnexAbility1(state));
	}
}
