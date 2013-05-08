package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kalastria Highborn")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.VAMPIRE})
@ManaCost("BB")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class KalastriaHighborn extends Card
{
	public static final class KalastriaHighbornAbility0 extends EventTriggeredAbility
	{
		public KalastriaHighbornAbility0(GameState state)
		{
			super(state, "Whenever Kalastria Highborn or another Vampire you control dies, you may pay (B). If you do, target player loses 2 life and you gain 2 life.");
			this.addPattern(whenThisDies());

			SetGenerator yourVampires = Intersect.instance(HasSubType.instance(SubType.VAMPIRE), ControlledBy.instance(You.instance()));
			SetGenerator yourOtherVampires = RelativeComplement.instance(yourVampires, AbilitySource.instance(This.instance()));
			this.addPattern(whenXIsPutIntoAGraveyardFromTheBattlefield(yourOtherVampires));

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

			EventFactory mayPay = new EventFactory(EventType.PLAYER_MAY_PAY_MANA, "You may pay (B)");
			mayPay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			mayPay.parameters.put(EventType.Parameter.COST, Identity.instance(new ManaPool("B")));
			mayPay.parameters.put(EventType.Parameter.PLAYER, You.instance());

			EventFactory lose = loseLife(target, 2, "Target player loses 2 life");
			EventFactory gain = gainLife(You.instance(), 2, "and you gain 2 life");
			EventFactory loseAndGain = sequence(lose, gain);

			EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may pay (B). If you do, target player loses 2 life and you gain 2 life.");
			effect.parameters.put(EventType.Parameter.IF, Identity.instance(mayPay));
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(loseAndGain));
			this.addEffect(effect);
		}
	}

	public KalastriaHighborn(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever Kalastria Highborn or another Vampire you control is put
		// into a graveyard from the battlefield, you may pay (B). If you do,
		// target player loses 2 life and you gain 2 life.
		this.addAbility(new KalastriaHighbornAbility0(state));
	}
}
