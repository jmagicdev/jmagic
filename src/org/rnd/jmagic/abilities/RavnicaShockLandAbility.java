package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class RavnicaShockLandAbility extends StaticAbility
{
	private final String thisName;

	public RavnicaShockLandAbility(GameState state, String thisName)
	{
		super(state, "As " + thisName + " enters the battlefield, you may pay 2 life. If you don't, " + thisName + " enters the battlefield tapped.");

		this.thisName = thisName;

		ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, "As " + thisName + " enters the battlefield, you may pay 2 life. If you don't, " + thisName + " enters the battlefield tapped.");
		replacement.addPattern(asThisEntersTheBattlefield());

		SetGenerator zoneChange = replacement.replacedByThis();

		EventFactory tap = new EventFactory(EventType.TAP_PERMANENTS, thisName + " enters the battlefield tapped");
		tap.parameters.put(EventType.Parameter.CAUSE, CauseOf.instance(zoneChange));
		tap.parameters.put(EventType.Parameter.OBJECT, NewObjectOf.instance(zoneChange));

		EventFactory payLife = new EventFactory(EventType.PAY_LIFE, "Pay 2 life");
		payLife.parameters.put(EventType.Parameter.CAUSE, CauseOf.instance(zoneChange));
		payLife.parameters.put(EventType.Parameter.PLAYER, You.instance());
		payLife.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));

		EventFactory ite = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may pay 2 life. If you don't, " + thisName + " enters the battlefield tapped.");
		ite.parameters.put(EventType.Parameter.IF, Identity.instance(youMay(payLife, "You may pay 2 life")));
		ite.parameters.put(EventType.Parameter.ELSE, Identity.instance(tap));
		replacement.addEffect(ite);

		this.addEffectPart(replacementEffectPart(replacement));

		this.canApply = NonEmpty.instance();
	}

	@Override
	public RavnicaShockLandAbility create(Game game)
	{
		return new RavnicaShockLandAbility(game.physicalState, this.thisName);
	}
}