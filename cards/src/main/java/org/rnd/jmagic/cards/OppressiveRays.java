package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Oppressive Rays")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class OppressiveRays extends Card
{
	public static final class OppressiveRaysAbility1 extends StaticAbility
	{
		public OppressiveRaysAbility1(GameState state)
		{
			super(state, "Enchanted creature can't attack or block unless its controller pays (3).");

			{
				ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_COST);
				part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.fromCollection(new ManaPool("3")));
				part.parameters.put(ContinuousEffectType.Parameter.OBJECT, EnchantedBy.instance(This.instance()));
				part.parameters.put(ContinuousEffectType.Parameter.PLAYER, Players.instance());
				this.addEffectPart(part);
			}

			{
				ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_COST);
				part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.fromCollection(new ManaPool("3")));
				part.parameters.put(ContinuousEffectType.Parameter.OBJECT, EnchantedBy.instance(This.instance()));
				this.addEffectPart(part);
			}
		}
	}

	public static final class OppressiveRaysAbility2 extends StaticAbility
	{
		public OppressiveRaysAbility2(GameState state)
		{
			super(state, "Activated abilities of enchanted creature cost (3) more to activate.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COST_ADDITION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, ActivatedAbilitiesOf.instance(EnchantedBy.instance(This.instance()), true));
			part.parameters.put(ContinuousEffectType.Parameter.COST, numberGenerator(3));
			this.addEffectPart(part);
		}
	}

	public OppressiveRays(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature can't attack or block unless its controller pays
		// (3).
		this.addAbility(new OppressiveRaysAbility1(state));

		// Activated abilities of enchanted creature cost (3) more to activate.
		this.addAbility(new OppressiveRaysAbility2(state));
	}
}
