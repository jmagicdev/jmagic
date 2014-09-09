package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Jinxed Idol")
@Types({Type.ARTIFACT})
@ManaCost("2")
@ColorIdentity({})
public final class JinxedIdol extends Card
{
	public static final class JinxedIdolAbility0 extends EventTriggeredAbility
	{
		public JinxedIdolAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, Jinxed Idol deals 2 damage to you.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			this.addEffect(permanentDealDamage(2, You.instance(), "Jinxed Idol deals 2 damage to you."));
		}
	}

	public static final class JinxedIdolAbility1 extends ActivatedAbility
	{
		public JinxedIdolAbility1(GameState state)
		{
			super(state, "Sacrifice a creature: Target opponent gains control of Jinxed Idol.");

			this.addCost(sacrificeACreature());

			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));

			ContinuousEffect.Part controlPart = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			controlPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			controlPart.parameters.put(ContinuousEffectType.Parameter.PLAYER, target);

			EventFactory factory = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "Target opponent gains control of Jinxed Idol.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.EFFECT, Identity.instance());
			factory.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(Empty.instance()));
			this.addEffect(factory);
		}
	}

	public JinxedIdol(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, Jinxed Idol deals 2 damage to you.
		this.addAbility(new JinxedIdolAbility0(state));

		// Sacrifice a creature: Target opponent gains control of Jinxed Idol.
		this.addAbility(new JinxedIdolAbility1(state));
	}
}
