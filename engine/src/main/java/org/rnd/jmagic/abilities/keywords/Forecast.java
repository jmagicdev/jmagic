package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

/**
 * 702.54. Forecast<br>
 * <br>
 * 702.54a A forecast ability is a special kind of activated ability that can be
 * activated only from a player's hand. It's written 'Forecast -- [Activated
 * ability].'<br>
 * <br>
 * 702.54b A forecast ability may be activated only during the upkeep step of
 * the card's owner and only once each turn. The controller of the forecast
 * ability reveals the card with that ability from his or her hand as the
 * ability is activated. That player plays with that card revealed in his or her
 * hand until it leaves the player's hand or until a step or phase that isn't an
 * upkeep step begins, whichever comes first.<br>
 * <br>
 * NOTE: All currently printed Forecast cards have "Reveal ~" written on the
 * card. Including this cost isn't necessary when constructing a forecast
 * ability since this class will reveal the card and keep it revealed for the
 * appropriate length of time (it's part of the rules). Do include it in the
 * text though.
 */
public abstract class Forecast extends ActivatedAbility
{
	public Forecast(GameState state, String name)
	{
		super(state, "Forecast \u2014 " + name);

		SetGenerator itsYourUpkeep = Intersect.instance(CurrentStep.instance(), UpkeepStepOf.instance(You.instance()));
		this.activateOnlyFromHand();
		this.addActivateRestriction(Not.instance(itsYourUpkeep));
		this.perTurnLimit(1);

		EventFactory reveal = new EventFactory(EventType.REVEAL, "Reveal this card");
		reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
		reveal.parameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
		reveal.parameters.put(EventType.Parameter.EFFECT, Identity.instance(Not.instance(itsYourUpkeep)));
		this.addCost(reveal);
	}
}
