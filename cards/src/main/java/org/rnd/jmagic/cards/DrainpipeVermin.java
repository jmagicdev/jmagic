package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Drainpipe Vermin")
@Types({Type.CREATURE})
@SubTypes({SubType.RAT})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class DrainpipeVermin extends Card
{
	public static final class DrainpipeVerminAbility0 extends EventTriggeredAbility
	{
		public DrainpipeVerminAbility0(GameState state)
		{
			super(state, "When Drainpipe Vermin dies, you may pay (B). If you do, target player discards a card.");
			this.addPattern(whenThisDies());

			EventFactory mayPay = new EventFactory(EventType.PLAYER_MAY_PAY_MANA, "You may pay (B)");
			mayPay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			mayPay.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("(B)")));
			mayPay.parameters.put(EventType.Parameter.PLAYER, You.instance());

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

			EventFactory ifThen = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may pay (B). If you do, target player discards a card..");
			ifThen.parameters.put(EventType.Parameter.IF, Identity.instance(mayPay));
			ifThen.parameters.put(EventType.Parameter.THEN, Identity.instance(discardCards(target, 1, "Target player discards a card.")));
			this.addEffect(ifThen);
		}
	}

	public DrainpipeVermin(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// When Drainpipe Vermin dies, you may pay (B). If you do, target player
		// discards a card.
		this.addAbility(new DrainpipeVerminAbility0(state));
	}
}
