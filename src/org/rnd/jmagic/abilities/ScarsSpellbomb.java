package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class ScarsSpellbomb extends EventTriggeredAbility
{
	private final String manaCost;
	private final String cardName;

	public ScarsSpellbomb(GameState state, String cardName, String manaCost)
	{
		super(state, "When " + cardName + " is put into a graveyard from the battlefield, you may pay " + manaCost + ". If you do, draw a card.");
		this.addPattern(whenThisIsPutIntoAGraveyardFromTheBattlefield());

		this.cardName = cardName;
		this.manaCost = manaCost;

		EventFactory mayPay = new EventFactory(EventType.PLAYER_MAY_PAY_MANA, "You may pay " + manaCost + ".");
		mayPay.parameters.put(EventType.Parameter.CAUSE, This.instance());
		mayPay.parameters.put(EventType.Parameter.COST, Identity.instance(new ManaPool(manaCost)));
		mayPay.parameters.put(EventType.Parameter.PLAYER, You.instance());

		EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may pay " + manaCost + ". If you do, draw a card.");
		effect.parameters.put(EventType.Parameter.IF, Identity.instance(mayPay));
		effect.parameters.put(EventType.Parameter.THEN, Identity.instance(drawACard()));
		this.addEffect(effect);
	}

	@Override
	public ScarsSpellbomb create(Game game)
	{
		return new ScarsSpellbomb(game.physicalState, this.cardName, this.manaCost);
	}
}