package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Phytotitan")
@Types({Type.CREATURE})
@SubTypes({SubType.PLANT, SubType.ELEMENTAL})
@ManaCost("4GG")
@ColorIdentity({Color.GREEN})
public final class Phytotitan extends Card
{
	public static final class PhytotitanAbility0 extends EventTriggeredAbility
	{
		public PhytotitanAbility0(GameState state)
		{
			super(state, "When Phytotitan dies, return it to the battlefield tapped under its owner's control at the beginning of his or her next upkeep.");
			this.addPattern(whenThisDies());

			SetGenerator it = NewObjectOf.instance(EventResult.instance(TriggerEvent.instance(This.instance())));
			SetGenerator owner = OwnerOf.instance(it);

			EventFactory returnTapped = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_TAPPED, "Return Phytotitan to the battlefield tapped under its owner's control.");
			returnTapped.parameters.put(EventType.Parameter.CAUSE, This.instance());
			returnTapped.parameters.put(EventType.Parameter.CONTROLLER, owner);
			returnTapped.parameters.put(EventType.Parameter.OBJECT, it);

			SimpleEventPattern nextUpkeep = new SimpleEventPattern(EventType.BEGIN_STEP);
			nextUpkeep.put(EventType.Parameter.STEP, UpkeepStepOf.instance(owner));

			EventFactory returnLater = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Return Phytotitan to the battlefield tapped under its owner's control at the beginning of his or her next upkeep.");
			returnLater.parameters.put(EventType.Parameter.CAUSE, This.instance());
			returnLater.parameters.put(EventType.Parameter.EFFECT, Identity.instance(returnTapped));
			returnLater.parameters.put(EventType.Parameter.EVENT, Identity.instance(nextUpkeep));
			this.addEffect(returnLater);
		}
	}

	public Phytotitan(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(2);

		// When Phytotitan dies, return it to the battlefield tapped under its
		// owner's control at the beginning of his or her next upkeep.
		this.addAbility(new PhytotitanAbility0(state));
	}
}
