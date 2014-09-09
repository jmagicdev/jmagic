package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Chronic Flooding")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class ChronicFlooding extends Card
{
	public static final class ChronicFloodingAbility1 extends EventTriggeredAbility
	{
		public ChronicFloodingAbility1(GameState state)
		{
			super(state, "Whenever enchanted land becomes tapped, its controller puts the top three cards of his or her library into his or her graveyard.");

			SetGenerator enchantedLand = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);

			SimpleEventPattern tapped = new SimpleEventPattern(EventType.TAP_ONE_PERMANENT);
			tapped.put(EventType.Parameter.OBJECT, enchantedLand);
			this.addPattern(tapped);

			this.addEffect(millCards(ControllerOf.instance(enchantedLand), 3, "Its controller puts the top three cards of his or her library into his or her graveyard."));
		}
	}

	public ChronicFlooding(GameState state)
	{
		super(state);

		// Enchant land
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Land(state));

		// Whenever enchanted land becomes tapped, its controller puts the top
		// three cards of his or her library into his or her graveyard.
		this.addAbility(new ChronicFloodingAbility1(state));
	}
}
