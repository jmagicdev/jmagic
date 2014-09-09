package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Trace of Abundance")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("(R/W)G")
@ColorIdentity({Color.WHITE, Color.GREEN, Color.RED})
public final class TraceofAbundance extends Card
{
	public static final class Trace extends StaticAbility
	{
		public Trace(GameState state)
		{
			super(state, "Enchanted land has shroud.");

			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Shroud.class));
		}
	}

	public static final class Abundance extends EventTriggeredAbility
	{
		public Abundance(GameState state)
		{
			super(state, "Whenever enchanted land is tapped for mana, its controller adds one mana of any color to his or her mana pool.");

			SetGenerator enchantedLand = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);

			this.addPattern(tappedForMana(Players.instance(), new SimpleSetPattern(enchantedLand)));

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			parameters.put(EventType.Parameter.MANA, Identity.fromCollection(new ManaPool("(WUBRG)")));
			parameters.put(EventType.Parameter.PLAYER, ControllerOf.instance(enchantedLand));
			this.addEffect(new EventFactory(EventType.ADD_MANA, parameters, "Its controller adds one mana of any color to his or her mana pool."));
		}
	}

	public TraceofAbundance(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Land(state));

		this.addAbility(new Trace(state));

		this.addAbility(new Abundance(state));
	}
}
