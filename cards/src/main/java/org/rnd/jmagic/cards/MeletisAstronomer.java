package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

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
			super(state, "Whenever you cast a spell that targets Meletis Astronomer, look at the top three cards of your library. You may reveal an enchantment card from among them and put it into your hand. Put the rest on the bottom of your library in any order.");
			this.addPattern(heroic());

			EventFactory effect = new EventFactory(PUT_ONE_FROM_TOP_N_OF_LIBRARY_INTO_HAND, "Look at the top five cards of your library. You may reveal a colorless card from among them and put it into your hand. Then put the rest on the bottom of your library in any order.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
			effect.parameters.put(EventType.Parameter.CARD, TopCards.instance(5, LibraryOf.instance(You.instance())));
			effect.parameters.put(EventType.Parameter.TYPE, HasType.instance(Type.ENCHANTMENT));
			this.addEffect(effect);
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
