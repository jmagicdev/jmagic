package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ancient Stirrings")
@Types({Type.SORCERY})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class AncientStirrings extends Card
{
	public AncientStirrings(GameState state)
	{
		super(state);

		// Look at the top five cards of your library. You may reveal a
		// colorless card from among them and put it into your hand. Then put
		// the rest on the bottom of your library in any order.
		EventFactory effect = new EventFactory(PUT_ONE_FROM_TOP_N_OF_LIBRARY_INTO_HAND, "Look at the top five cards of your library. You may reveal a colorless card from among them and put it into your hand. Then put the rest on the bottom of your library in any order.");
		effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
		effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
		effect.parameters.put(EventType.Parameter.CARD, TopCards.instance(5, LibraryOf.instance(You.instance())));
		effect.parameters.put(EventType.Parameter.TYPE, Colorless.instance());
		this.addEffect(effect);
	}
}
