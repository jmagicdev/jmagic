package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("\u00C6therspouts")
@Types({Type.INSTANT})
@ManaCost("3UU")
@ColorIdentity({Color.BLUE})
public final class AEtherspouts extends Card
{
	public static PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("AEtherspouts", "Put any number of attacking creatures you own on top of your library; the rest will be put on the bottom.", true);

	public AEtherspouts(GameState state)
	{
		super(state);

		// For each attacking creature, its owner puts it on the top or bottom
		// of his or her library.

		SetGenerator players = OwnerOf.instance(Attacking.instance());
		DynamicEvaluation eachPlayer = DynamicEvaluation.instance();

		SetGenerator playersAttackers = Intersect.instance(Attacking.instance(), OwnedBy.instance(eachPlayer));
		EventFactory onePlayerChooses = playerChoose(eachPlayer, Between.instance(0, null), playersAttackers, PlayerInterface.ChoiceType.OBJECTS, REASON, "Choose attacking creatures to put on top");

		EventFactory chooseAll = new EventFactory(FOR_EACH_PLAYER, "For each attacking creature, its owner");
		chooseAll.parameters.put(EventType.Parameter.PLAYER, players);
		chooseAll.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachPlayer));
		chooseAll.parameters.put(EventType.Parameter.EFFECT, Identity.instance(onePlayerChooses));
		this.addEffect(chooseAll);

		SetGenerator chosen = ForEachResult.instance(chooseAll, players);

		EventFactory toTop = putOnTopOfLibrary(chosen, "puts it on the top");
		EventFactory toBottom = putOnBottomOfLibrary(chosen, "or bottom of his or her library.");
		this.addEffect(simultaneous(toTop, toBottom));
	}
}
