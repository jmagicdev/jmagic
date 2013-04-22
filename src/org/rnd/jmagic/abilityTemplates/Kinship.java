package org.rnd.jmagic.abilityTemplates;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public abstract class Kinship extends EventTriggeredAbility
{
	public Kinship(GameState state, String effectName)
	{
		super(state, effectName);

		this.addPattern(atTheBeginningOfYourUpkeep());

		// At the beginning of your upkeep, you may look at the top card of your
		// library. If it shares a creature type with Sensation Gorger, you may
		// reveal it. If you do,

		SetGenerator topCard = TopCards.instance(1, LibraryOf.instance(You.instance()));

		EventFactory lookFactory = new EventFactory(EventType.LOOK, "Look at the top card of your library.");
		lookFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		lookFactory.parameters.put(EventType.Parameter.OBJECT, topCard);
		lookFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());

		EventFactory mayLookFactory = new EventFactory(EventType.PLAYER_MAY, "You may look at the top card of your library.");
		mayLookFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		mayLookFactory.parameters.put(EventType.Parameter.EVENT, Identity.instance(lookFactory));

		EventFactory revealFactory = new EventFactory(EventType.REVEAL, "Reveal it.");
		revealFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		revealFactory.parameters.put(EventType.Parameter.OBJECT, topCard);

		EventFactory mayRevealFactory = new EventFactory(EventType.PLAYER_MAY, "You may reveal it.");
		mayRevealFactory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		mayRevealFactory.parameters.put(EventType.Parameter.EVENT, Identity.instance(revealFactory));

		EventFactory ifRevealFactory = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may reveal it.  If you do, ...");
		ifRevealFactory.parameters.put(EventType.Parameter.IF, Identity.instance(mayRevealFactory));
		ifRevealFactory.parameters.put(EventType.Parameter.THEN, Identity.instance(getKinshipEffect()));

		EventFactory ifShareFactory = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If it shares a creature type with this, you may reveal it. If you do, ...");
		ifShareFactory.parameters.put(EventType.Parameter.IF, Intersect.instance(SubTypesOf.instance(topCard, Type.CREATURE), SubTypesOf.instance(ABILITY_SOURCE_OF_THIS, Type.CREATURE)));
		ifShareFactory.parameters.put(EventType.Parameter.THEN, Identity.instance(ifRevealFactory));

		// TODO : lol, ellipses at the end of an abstract event name
		EventFactory ifLookFactory = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may look at the top card of your library. If it shares a creature type with this, you may reveal it. If you do, ...");
		ifLookFactory.parameters.put(EventType.Parameter.IF, Identity.instance(mayLookFactory));
		ifLookFactory.parameters.put(EventType.Parameter.THEN, Identity.instance(ifShareFactory));
		this.addEffect(ifLookFactory);
	}

	protected abstract EventFactory getKinshipEffect();
}
