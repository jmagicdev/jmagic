package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rite of Ruin")
@Types({Type.SORCERY})
@ManaCost("5RR")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class RiteofRuin extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("RiteofRuin", "Choose an order for artifacts, creatures, and lands.", true);

	public RiteofRuin(GameState state)
	{
		super(state);

		// Choose an order for artifacts, creatures, and lands. Each player
		// sacrifices one permanent of the first type, sacrifices two of the
		// second type, then sacrifices three of the third type.
		EventFactory choose = playerChoose(You.instance(), 3, Identity.instance(Type.ARTIFACT, Type.CREATURE, Type.LAND), PlayerInterface.ChoiceType.ENUM_ORDERED, REASON, "Choose an order for artifacts, creatures, and lands.");
		choose.parameters.put(EventType.Parameter.ORDERED, Empty.instance());
		this.addEffect(choose);

		SetGenerator order = EffectResult.instance(choose);

		{
			DynamicEvaluation player = DynamicEvaluation.instance();
			EventFactory factory = new EventFactory(FOR_EACH_PLAYER, "Each player sacrifices one permanent of the first type,");
			factory.parameters.put(EventType.Parameter.TARGET, Identity.instance(player));
			factory.parameters.put(EventType.Parameter.EFFECT, Identity.instance(sacrifice(player, 1, HasType.instance(ListGet.instance(order, 0)), "Each player sacrifices one permanent of the first type,")));
			this.addEffect(factory);
		}

		{
			DynamicEvaluation player = DynamicEvaluation.instance();
			EventFactory factory = new EventFactory(FOR_EACH_PLAYER, "sacrifices two of the second type,");
			factory.parameters.put(EventType.Parameter.TARGET, Identity.instance(player));
			factory.parameters.put(EventType.Parameter.EFFECT, Identity.instance(sacrifice(player, 2, HasType.instance(ListGet.instance(order, 1)), "sacrifices two of the second type,")));
			this.addEffect(factory);
		}

		{
			DynamicEvaluation player = DynamicEvaluation.instance();
			EventFactory factory = new EventFactory(FOR_EACH_PLAYER, "then sacrifices three of the third type.");
			factory.parameters.put(EventType.Parameter.TARGET, Identity.instance(player));
			factory.parameters.put(EventType.Parameter.EFFECT, Identity.instance(sacrifice(player, 3, HasType.instance(ListGet.instance(order, 2)), "then sacrifices three of the third type.")));
			this.addEffect(factory);
		}
	}
}
