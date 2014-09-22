package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Avalanche Tusker")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.ELEPHANT})
@ManaCost("2GUR")
@ColorIdentity({Color.BLUE, Color.RED, Color.GREEN})
public final class AvalancheTusker extends Card
{
	public static final class AvalancheTuskerAbility0 extends EventTriggeredAbility
	{
		public AvalancheTuskerAbility0(GameState state)
		{
			super(state, "Whenever Avalanche Tusker attacks, target creature defending player controls blocks it this combat if able.");
			this.addPattern(whenThisAttacks());

			SetGenerator defender = DefendingPlayer.instance(ABILITY_SOURCE_OF_THIS);
			SetGenerator creatures = Intersect.instance(HasType.instance(Type.CREATURE), ControlledBy.instance(defender));
			SetGenerator target = targetedBy(this.addTarget(creatures, "target creature defending player controls"));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_REQUIREMENT);
			part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, ABILITY_SOURCE_OF_THIS);
			part.parameters.put(ContinuousEffectType.Parameter.DEFENDING, target);
			this.addEffect(createFloatingEffect("Target creature defending player controls blocks it this combat if able.", part));
		}
	}

	public AvalancheTusker(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(4);

		// Whenever Avalanche Tusker attacks, target creature defending player
		// controls blocks it this combat if able.
		this.addAbility(new AvalancheTuskerAbility0(state));
	}
}
