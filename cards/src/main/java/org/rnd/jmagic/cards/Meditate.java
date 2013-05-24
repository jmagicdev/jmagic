package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Meditate")
@Types({Type.INSTANT})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.TEMPEST, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class Meditate extends Card
{
	public Meditate(GameState state)
	{
		super(state);

		this.addEffect(drawCards(You.instance(), 4, "Draw four cards."));

		SimpleEventPattern yourNextTurn = new SimpleEventPattern(EventType.BEGIN_TURN);
		yourNextTurn.put(EventType.Parameter.TURN, TurnOf.instance(You.instance()));

		EventReplacementEffect skipReplacement = new EventReplacementEffect(this.game, "You skip your next turn.", yourNextTurn);

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REPLACEMENT_EFFECT);
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(skipReplacement));

		EventType.ParameterMap skipParameters = new EventType.ParameterMap();
		skipParameters.put(EventType.Parameter.CAUSE, This.instance());
		skipParameters.put(EventType.Parameter.EFFECT, Identity.instance(part));
		skipParameters.put(EventType.Parameter.EXPIRES, Identity.instance(Empty.instance()));
		skipParameters.put(EventType.Parameter.USES, numberGenerator(1));
		this.addEffect(new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, skipParameters, "Skip your next turn."));
	}
}
