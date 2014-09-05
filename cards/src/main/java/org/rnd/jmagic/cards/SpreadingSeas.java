package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Spreading Seas")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class SpreadingSeas extends Card
{
	public static final class ETBDraw extends EventTriggeredAbility
	{
		public ETBDraw(GameState state)
		{
			super(state, "When Spreading Seas enters the battlefield, draw a card.");

			this.addPattern(whenThisEntersTheBattlefield());

			this.addEffect(drawCards(You.instance(), 1, "Draw a card"));
		}
	}

	public static final class Spread extends StaticAbility
	{
		public Spread(GameState state)
		{
			super(state, "Enchanted land is an Island.");

			SetGenerator enchantedLand = EnchantedBy.instance(This.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.SET_TYPES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, enchantedLand);
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(SubType.ISLAND));
			this.addEffectPart(part);
		}
	}

	public SpreadingSeas(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Land(state));

		this.addAbility(new ETBDraw(state));

		this.addAbility(new Spread(state));
	}
}
