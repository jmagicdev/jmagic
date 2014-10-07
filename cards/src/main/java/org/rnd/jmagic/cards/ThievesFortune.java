package org.rnd.jmagic.cards;

import org.rnd.jmagic.Convenience.Sifter;
import org.rnd.jmagic.engine.*;

@Name("Thieves' Fortune")
@Types({Type.INSTANT, Type.TRIBAL})
@SubTypes({SubType.ROGUE})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class ThievesFortune extends Card
{
	public ThievesFortune(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Prowl(state, "(U)"));
		this.addEffect(Sifter.start().look(4).take(1).dumpToBottom().getEventFactory("Look at the top four cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order."));
	}
}
