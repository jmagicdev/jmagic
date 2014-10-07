package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Daring Thief")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ROGUE})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class DaringThief extends Card
{
	public static final class DaringThiefAbility0 extends EventTriggeredAbility
	{
		public DaringThiefAbility0(GameState state)
		{
			super(state, "Inspired \u2014 Whenever Daring Thief becomes untapped, you may exchange control of target nonland permanent you control and target permanent an opponent controls that shares a card type with it.");
			this.addPattern(inspired());

			SetGenerator yourNonlands = RelativeComplement.instance(ControlledBy.instance(You.instance()), HasType.instance(Type.LAND));
			SetGenerator yours = targetedBy(this.addTarget(yourNonlands, "target nonland permanent you control"));

			SetGenerator stuff = HasType.instance(TypesOf.instance(yours));
			SetGenerator enemyStuff = Intersect.instance(ControlledBy.instance(OpponentsOf.instance(You.instance())), stuff);
			SetGenerator theirs = targetedBy(this.addTarget(enemyStuff, "target permanent an opponent controls that shares a card type with it"));

			EventFactory factory = new EventFactory(EventType.EXCHANGE_CONTROL, "Exchange control target nonland permanent you control and target permanent an opponent controls that shares a card type with it");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.OBJECT, Union.instance(yours, theirs));
			this.addEffect(youMay(factory));
		}
	}

	public DaringThief(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Inspired \u2014 Whenever Daring Thief becomes untapped, you may
		// exchange control of target nonland permanent you control and target
		// permanent an opponent controls that shares a card type with it.
		this.addAbility(new DaringThiefAbility0(state));
	}
}
