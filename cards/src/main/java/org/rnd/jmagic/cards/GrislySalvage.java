package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Grisly Salvage")
@Types({Type.INSTANT})
@ManaCost("BG")
@ColorIdentity({Color.BLACK, Color.GREEN})
public final class GrislySalvage extends Card
{
	public GrislySalvage(GameState state)
	{
		super(state);

		SetGenerator stuff = HasType.instance(Type.CREATURE, Type.LAND);
		this.addEffect(Sifter.start().reveal(5).take(1, stuff).dumpToGraveyard().getEventFactory("Reveal the top five cards of your library. You may put a creature or land card from among them into your hand. Put the rest into your graveyard."));
	}
}
