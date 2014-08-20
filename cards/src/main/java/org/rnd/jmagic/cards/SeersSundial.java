package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Seer's Sundial")
@Types({Type.ARTIFACT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.RARE)})
@ColorIdentity({})
public final class SeersSundial extends Card
{
	public static final class SeersSundialAbility0 extends EventTriggeredAbility
	{
		public SeersSundialAbility0(GameState state)
		{
			super(state, "Whenever a land enters the battlefield under your control, you may pay (2). If you do, draw a card.");
			this.addPattern(landfall());

			EventFactory mayPay2 = new EventFactory(EventType.PLAYER_MAY_PAY_MANA, "You may pay (2)");
			mayPay2.parameters.put(EventType.Parameter.CAUSE, This.instance());
			mayPay2.parameters.put(EventType.Parameter.PLAYER, You.instance());
			mayPay2.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("(2)")));

			EventFactory drawACard = drawACard();

			EventFactory totalEffect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may pay (R). If you do, return Punishing Fire from your graveyard to your hand.");
			totalEffect.parameters.put(EventType.Parameter.IF, Identity.instance(mayPay2));
			totalEffect.parameters.put(EventType.Parameter.THEN, Identity.instance(drawACard));
			this.addEffect(totalEffect);
		}
	}

	public SeersSundial(GameState state)
	{
		super(state);

		// Landfall \u2014 Whenever a land enters the battlefield under your
		// control, you may pay (2). If you do, draw a card.
		this.addAbility(new SeersSundialAbility0(state));
	}
}
