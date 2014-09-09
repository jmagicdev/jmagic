package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hypnotic Specter")
@Types({Type.CREATURE})
@SubTypes({SubType.SPECTER})
@ManaCost("1BB")
@ColorIdentity({Color.BLACK})
public final class HypnoticSpecter extends Card
{
	public static final class DementiaStrike extends EventTriggeredAbility
	{
		public DementiaStrike(GameState state)
		{
			super(state, "Whenever Hypnotic Specter deals damage to an opponent, that player discards a card at random.");

			this.addPattern(whenDealsDamageToAnOpponent(ABILITY_SOURCE_OF_THIS));

			SetGenerator opponents = OpponentsOf.instance(ControllerOf.instance(ABILITY_SOURCE_OF_THIS));
			SetGenerator trigger = TriggerDamage.instance(This.instance());
			SetGenerator takers = Intersect.instance(opponents, TakerOfDamage.instance(DamageDealtBy.instance(ABILITY_SOURCE_OF_THIS, trigger)));

			EventType.ParameterMap discardParameters = new EventType.ParameterMap();
			discardParameters.put(EventType.Parameter.CAUSE, This.instance());
			discardParameters.put(EventType.Parameter.PLAYER, takers);
			discardParameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			this.addEffect(new EventFactory(EventType.DISCARD_RANDOM, discardParameters, "That player discards a card at random"));
		}
	}

	public HypnoticSpecter(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new DementiaStrike(state));
	}
}
