package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.Convenience.Sifter;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Meletis Astronomer")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class MeletisAstronomer extends Card
{
	public static final class MeletisAstronomerAbility0 extends EventTriggeredAbility
	{
		public MeletisAstronomerAbility0(GameState state)
		{
			super(state, "Heroic \u2014 Whenever you cast a spell that targets Meletis Astronomer, look at the top three cards of your library. You may reveal an enchantment card from among them and put it into your hand. Put the rest on the bottom of your library in any order.");
			this.addPattern(heroic());
			this.addEffect(Sifter.start().look(3).take(1, HasType.instance(Type.ENCHANTMENT)).dumpToBottom().getEventFactory("Look at the top three cards of your library. You may reveal an enchantment card from among them and put it into your hand. Put the rest on the bottom of your library in any order."));
		}
	}

	public MeletisAstronomer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// Heroic \u2014 Whenever you cast a spell that targets Meletis
		// Astronomer, look at the top three cards of your library. You may
		// reveal an enchantment card from among them and put it into your hand.
		// Put the rest on the bottom of your library in any order.
		this.addAbility(new MeletisAstronomerAbility0(state));
	}
}
