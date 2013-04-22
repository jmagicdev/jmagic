package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Contaminated Ground")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class ContaminatedGround extends Card
{
	public static final class Contaminate extends StaticAbility
	{
		public Contaminate(GameState state)
		{
			super(state, "Enchanted land is a Swamp.");

			SetGenerator enchantedLand = EnchantedBy.instance(This.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.SET_TYPES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, enchantedLand);
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(SubType.SWAMP));
			this.addEffectPart(part);
		}
	}

	public static final class LifeLoss extends EventTriggeredAbility
	{
		public LifeLoss(GameState state)
		{
			super(state, "Whenever enchanted land becomes tapped, its controller loses 2 life.");

			SetGenerator enchantedLand = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);

			SimpleEventPattern tapped = new SimpleEventPattern(EventType.TAP_ONE_PERMANENT);
			tapped.put(EventType.Parameter.OBJECT, enchantedLand);
			this.addPattern(tapped);

			this.addEffect(loseLife(ControllerOf.instance(enchantedLand), 2, "Its controller loses 2 life."));
		}
	}

	public ContaminatedGround(GameState state)
	{
		super(state);

		// Enchant land
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Land(state));

		// Enchanted land is a Swamp.
		this.addAbility(new Contaminate(state));

		// Whenever enchanted land becomes tapped, its controller loses 2 life.
		this.addAbility(new LifeLoss(state));
	}
}
