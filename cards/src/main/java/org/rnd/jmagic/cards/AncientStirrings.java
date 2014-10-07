package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ancient Stirrings")
@Types({Type.SORCERY})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class AncientStirrings extends Card
{
	public AncientStirrings(GameState state)
	{
		super(state);

		// Look at the top five cards of your library. You may reveal a
		// colorless card from among them and put it into your hand. Then put
		// the rest on the bottom of your library in any order.
		this.addEffect(Sifter.start().look(5).take(1, Colorless.instance()).dumpToBottom().getEventFactory("Look at the top five cards of your library. You may reveal a colorless card from among them and put it into your hand. Then put the rest on the bottom of your library in any order."));
	}
}
