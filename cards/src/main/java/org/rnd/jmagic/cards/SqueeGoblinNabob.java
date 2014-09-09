package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Squee, Goblin Nabob")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class SqueeGoblinNabob extends Card
{
	public static final class SqueeDigger extends EventTriggeredAbility
	{
		public SqueeDigger(GameState state)
		{
			super(state, "At the beginning of your upkeep, you may return Squee, Goblin Nabob from your graveyard to your hand.");

			this.addPattern(atTheBeginningOfYourUpkeep());
			this.triggersFromGraveyard();

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;
			SetGenerator owner = OwnerOf.instance(thisCard);

			EventFactory move = new EventFactory(EventType.MOVE_OBJECTS, "Return Squee, Goblin Nabob from your graveyard to your hand");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.TO, HandOf.instance(owner));
			move.parameters.put(EventType.Parameter.OBJECT, thisCard);

			this.addEffect(youMay(move, "You may return Squee, Goblin Nabob from your graveyard to your hand."));
		}
	}

	public SqueeGoblinNabob(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new SqueeDigger(state));
	}
}
