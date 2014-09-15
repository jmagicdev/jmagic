package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Whims of the Fates")
@Types({Type.SORCERY})
@ManaCost("5R")
@ColorIdentity({Color.RED})
public final class WhimsoftheFates extends Card
{
	public WhimsoftheFates(GameState state)
	{
		super(state);

		// Starting with you, each player separates all permanents he or she
		// controls into three piles.

		DynamicEvaluation eachPlayer = DynamicEvaluation.instance();
		SetGenerator theirPermanents = ControlledBy.instance(eachPlayer);
		EventFactory eachSeparate = separateIntoPiles(eachPlayer, theirPermanents, 3, "Separate all permanents you control into three piles");

		EventFactory allSeparate = new EventFactory(FOR_EACH_PLAYER, "Starting with you, each player separates all permanents he or she controls into three piles.");
		allSeparate.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachPlayer));
		allSeparate.parameters.put(EventType.Parameter.EFFECT, Identity.instance(eachSeparate));
		this.addEffect(allSeparate);

		// Then each player chooses one of his or her piles at random
		SetGenerator theirPiles = ForEachResult.instance(allSeparate, eachPlayer);

		EventFactory eachChoose = new EventFactory(RANDOM, "Choose one of your piles at random");
		eachChoose.parameters.put(EventType.Parameter.OBJECT, theirPiles);

		EventFactory allChoose = new EventFactory(FOR_EACH_PLAYER, "Then each player chooses one of his or her piles at random");
		allChoose.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachPlayer));
		allChoose.parameters.put(EventType.Parameter.EFFECT, Identity.instance(eachChoose));
		this.addEffect(allChoose);

		// and sacrifices those permanents.
		SetGenerator chosenPermanents = ExplodeCollections.instance(ForEachResult.instance(allChoose, eachPlayer));
		EventFactory eachSacrifice = new EventFactory(EventType.SACRIFICE_PERMANENTS, "Sacrifice the chosen permanents");
		eachSacrifice.parameters.put(EventType.Parameter.CAUSE, This.instance());
		eachSacrifice.parameters.put(EventType.Parameter.PLAYER, eachPlayer);
		eachSacrifice.parameters.put(EventType.Parameter.PERMANENT, chosenPermanents);

		EventFactory allSacrifice = new EventFactory(FOR_EACH_PLAYER, "and sacrifices those permanents.");
		allSacrifice.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachPlayer));
		allSacrifice.parameters.put(EventType.Parameter.EFFECT, Identity.instance(eachSacrifice));
		this.addEffect(allSacrifice);
	}
}
