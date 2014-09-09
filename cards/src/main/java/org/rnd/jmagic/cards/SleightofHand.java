package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sleight of Hand")
@Types({Type.SORCERY})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class SleightofHand extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("SleightofHand", "Put a card into your hand.", false);

	public SleightofHand(GameState state)
	{
		super(state);

		// Look at the top two cards of your library.
		SetGenerator yourLibrary = LibraryOf.instance(You.instance());
		SetGenerator top2 = TopCards.instance(2, yourLibrary);

		EventFactory look = new EventFactory(EventType.LOOK, "Look at the top two cards of your library.");
		look.parameters.put(EventType.Parameter.CAUSE, This.instance());
		look.parameters.put(EventType.Parameter.OBJECT, top2);
		look.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(look);

		// Put one of them into your hand and the other on the bottom of your
		// library.
		EventFactory choose = playerChoose(You.instance(), 1, top2, PlayerInterface.ChoiceType.OBJECTS, REASON, "");
		this.addEffect(choose);

		SetGenerator chosen = EffectResult.instance(choose);
		EventFactory toHand = putIntoHand(chosen, You.instance(), "Put one of them into your hand");
		EventFactory toBottom = putOnBottomOfLibrary(RelativeComplement.instance(top2, chosen), "and the other on the bottom of your library.");
		this.addEffect(simultaneous(toHand, toBottom));
	}
}
