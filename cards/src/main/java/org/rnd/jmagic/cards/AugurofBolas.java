package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Augur of Bolas")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.MERFOLK})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class AugurofBolas extends Card
{
	public static final class AugurofBolasAbility0 extends EventTriggeredAbility
	{
		public AugurofBolasAbility0(GameState state)
		{
			super(state, "When Augur of Bolas enters the battlefield, look at the top three cards of your library. You may reveal an instant or sorcery card from among them and put it into your hand. Put the rest on the bottom of your library in any order.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory put = new EventFactory(PUT_ONE_FROM_TOP_N_OF_LIBRARY_INTO_HAND, "Look at the top three cards of your library. You may reveal an instant or sorcery card from among them and put it into your hand. Put the rest on the bottom of your library in any order.");
			put.parameters.put(EventType.Parameter.CAUSE, This.instance());
			put.parameters.put(EventType.Parameter.PLAYER, You.instance());
			put.parameters.put(EventType.Parameter.CARD, TopCards.instance(3, LibraryOf.instance(You.instance())));
			put.parameters.put(EventType.Parameter.TYPE, HasType.instance(Type.INSTANT, Type.SORCERY));
			this.addEffect(put);
		}
	}

	public AugurofBolas(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// When Augur of Bolas enters the battlefield, look at the top three
		// cards of your library. You may reveal an instant or sorcery card from
		// among them and put it into your hand. Put the rest on the bottom of
		// your library in any order.
		this.addAbility(new AugurofBolasAbility0(state));
	}
}
