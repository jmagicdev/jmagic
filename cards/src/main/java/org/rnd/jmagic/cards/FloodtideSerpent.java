package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Floodtide Serpent")
@Types({Type.CREATURE})
@SubTypes({SubType.SERPENT})
@ManaCost("4U")
@ColorIdentity({Color.BLUE})
public final class FloodtideSerpent extends Card
{
	public static final class FloodtideSerpentAbility0 extends StaticAbility
	{
		public FloodtideSerpentAbility0(GameState state)
		{
			super(state, "Floodtide Serpent can't attack unless you return an enchantment you control to its owner's hand.");

			SetGenerator yourEnchantments = Intersect.instance(ControlledBy.instance(You.instance()), HasType.instance(Type.ENCHANTMENT));
			EventFactory bounceEnchantment = bounceChoice(You.instance(), 1, yourEnchantments, "Return an enchantment you control to its owner's hand");

			ContinuousEffect.Part attackCost = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_COST);
			attackCost.parameters.put(ContinuousEffectType.Parameter.COST, Identity.instance(bounceEnchantment));
			attackCost.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			this.addEffectPart(attackCost);
		}
	}

	public FloodtideSerpent(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Floodtide Serpent can't attack unless you return an enchantment you
		// control to its owner's hand. (This cost is paid as attackers are
		// declared.)
		this.addAbility(new FloodtideSerpentAbility0(state));
	}
}
