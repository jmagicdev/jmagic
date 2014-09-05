package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Stream of Unconsciousness")
@Types({Type.INSTANT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Morningtide.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class StreamofUnconsciousness extends Card
{
	public StreamofUnconsciousness(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), (-4), (-0), "Target creature gets -4/-0 until end of turn."));

		EventFactory draw = drawCards(You.instance(), 1, "Draw a card");

		EventType.ParameterMap ifThenParameters = new EventType.ParameterMap();
		ifThenParameters.put(EventType.Parameter.IF, Intersect.instance(HasSubType.instance(SubType.WIZARD), ControlledBy.instance(You.instance())));
		ifThenParameters.put(EventType.Parameter.THEN, Identity.instance(draw));
		this.addEffect(new EventFactory(EventType.IF_CONDITION_THEN_ELSE, ifThenParameters, "If you control a Wizard, draw a card."));
	}
}
