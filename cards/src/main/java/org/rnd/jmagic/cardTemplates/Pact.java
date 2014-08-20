package org.rnd.jmagic.cardTemplates;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public abstract class Pact extends Card
{
	public static Game.LoseReason LOSE_REASON = new Game.LoseReason("Pact");

	public Pact(GameState state)
	{
		super(state);

		this.setColorIndicator(this.getColor());

		this.addEffects();

		String upkeepCost = this.getUpkeepCost();

		EventFactory payFactory = new EventFactory(EventType.PAY_MANA, ("Pay " + upkeepCost + "."));
		payFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		payFactory.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool(upkeepCost)));
		payFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		payFactory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));

		EventFactory loseFactory = new EventFactory(EventType.LOSE_GAME, "You lose the game.");
		loseFactory.parameters.put(EventType.Parameter.CAUSE, Union.instance(This.instance(), Identity.instance(Pact.LOSE_REASON)));
		loseFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());

		EventFactory ifFactory = new EventFactory(EventType.IF_EVENT_THEN_ELSE, ("Pay " + upkeepCost + ". If you don't, you lose the game."));
		ifFactory.parameters.put(EventType.Parameter.IF, Identity.instance(payFactory));
		ifFactory.parameters.put(EventType.Parameter.ELSE, Identity.instance(loseFactory));

		EventFactory triggerFactory = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, ("\n\nAt the beginning of your next upkeep, pay " + upkeepCost + ". If you don't, you lose the game."));
		triggerFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		triggerFactory.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfYourUpkeep()));
		triggerFactory.parameters.put(EventType.Parameter.EFFECT, Identity.instance(ifFactory));
		this.addEffect(triggerFactory);
	}

	public abstract Color getColor();

	public abstract void addEffects();

	public abstract String getUpkeepCost();
}
