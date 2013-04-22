package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Break of Day")
@Types({Type.INSTANT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class BreakofDay extends Card
{
	public BreakofDay(GameState state)
	{
		super(state);

		// Creatures you control get +1/+1 until end of turn.
		this.addEffect(ptChangeUntilEndOfTurn(CREATURES_YOU_CONTROL, +1, +1, "Creatures you control get +1/+1 until end of turn."));

		// Fateful hour \u2014 If you have 5 or less life, those creatures also
		// are indestructible this turn. (Lethal damage and effects that say
		// "destroy" don't destroy them.)
		EventFactory grant = createFloatingEffect("Those creatures also are indestructible this turn.", indestructible(CREATURES_YOU_CONTROL));

		EventFactory ifThen = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If you have 5 or less life, those creatures also are indestructible this turn.");
		ifThen.parameters.put(EventType.Parameter.IF, FatefulHour.instance());
		ifThen.parameters.put(EventType.Parameter.THEN, Identity.instance(grant));
		this.addEffect(ifThen);
	}
}
