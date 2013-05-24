package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Elephant Guide")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.JUDGMENT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class ElephantGuide extends Card
{
	public static final class ElephantPump extends StaticAbility
	{
		public ElephantPump(GameState state)
		{
			super(state, "Enchanted creature gets +3/+3.");

			this.addEffectPart(modifyPowerAndToughness(EnchantedBy.instance(This.instance()), 3, 3));
		}
	}

	public static final class ElephantGrave extends EventTriggeredAbility
	{
		public ElephantGrave(GameState state)
		{
			super(state, "When enchanted creature dies, put a 3/3 green Elephant creature token onto the battlefield.");

			this.addPattern(whenXDies(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS)));

			CreateTokensFactory token = new CreateTokensFactory(1, 3, 3, "Put a 3/3 green Elephant creature token onto the battlefield.");
			token.setColors(Color.GREEN);
			token.setSubTypes(SubType.ELEPHANT);
			this.addEffect(token.getEventFactory());
		}
	}

	public ElephantGuide(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +3/+3.
		this.addAbility(new ElephantPump(state));

		// When enchanted creature is put into a graveyard, put a 3/3 green
		// Elephant creature token onto the battlefield.
		this.addAbility(new ElephantGrave(state));
	}
}
