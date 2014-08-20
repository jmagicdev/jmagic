package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lorthos, the Tidemaker")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.OCTOPUS})
@ManaCost("5UUU")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE})
public final class LorthostheTidemaker extends Card
{
	public static final class TentacleRape extends EventTriggeredAbility
	{
		public TentacleRape(GameState state)
		{
			super(state, "Whenever Lorthos, the Tidemaker attacks, you may pay (8). If you do, tap up to eight target permanents. Those permanents don't untap during their controllers' next untap steps.");
			this.addPattern(whenThisAttacks());

			Target target = this.addTarget(Permanents.instance(), "up to eight target permanents");
			target.setNumber(0, 8);

			EventFactory mayPay8 = new EventFactory(EventType.PLAYER_MAY_PAY_MANA, "You may pay (8)");
			mayPay8.parameters.put(EventType.Parameter.CAUSE, This.instance());
			mayPay8.parameters.put(EventType.Parameter.PLAYER, You.instance());
			mayPay8.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("(8)")));

			EventFactory tapHard = new EventFactory(EventType.TAP_HARD, "Tap up to eight target permanents. Those permanents don't untap during their controllers' next untap steps.");
			tapHard.parameters.put(EventType.Parameter.CAUSE, This.instance());
			tapHard.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));

			EventFactory totalEffect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may pay (R). If you do, return Punishing Fire from your graveyard to your hand.");
			totalEffect.parameters.put(EventType.Parameter.IF, Identity.instance(mayPay8));
			totalEffect.parameters.put(EventType.Parameter.THEN, Identity.instance(tapHard));
			this.addEffect(totalEffect);
		}
	}

	public LorthostheTidemaker(GameState state)
	{
		super(state);

		this.setPower(8);
		this.setToughness(8);

		// Whenever Lorthos, the Tidemaker attacks, you may pay (8). If you do,
		// tap up to eight target permanents. Those permanents don't untap
		// during their controllers' next untap steps.
		this.addAbility(new TentacleRape(state));
	}
}
