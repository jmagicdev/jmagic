package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Goblin Lackey")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class GoblinLackey extends Card
{
	public static final class GoblinLackeyAbility0 extends EventTriggeredAbility
	{
		public GoblinLackeyAbility0(GameState state)
		{
			super(state, "Whenever Goblin Lackey deals damage to a player, you may put a Goblin permanent card from your hand onto the battlefield.");

			this.addPattern(whenDealsDamageToAPlayer(ABILITY_SOURCE_OF_THIS));

			EventFactory battlefield = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_CHOICE, "Put a Goblin permanent card from your hand onto the battlefield.");
			battlefield.parameters.put(EventType.Parameter.CAUSE, This.instance());
			battlefield.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			battlefield.parameters.put(EventType.Parameter.OBJECT, Intersect.instance(InZone.instance(HandOf.instance(You.instance())), HasSubType.instance(SubType.GOBLIN), HasType.instance(Type.permanentTypes())));
			this.addEffect(youMay(battlefield, "You may put a Goblin permanent card from your hand onto the battlefield."));
		}
	}

	public GoblinLackey(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Whenever Goblin Lackey deals damage to a player, you may put a Goblin
		// permanent card from your hand onto the battlefield.
		this.addAbility(new GoblinLackeyAbility0(state));
	}
}
