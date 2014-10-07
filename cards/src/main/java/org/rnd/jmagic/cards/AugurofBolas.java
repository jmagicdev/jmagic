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

			SetGenerator spellCards = HasType.instance(Type.INSTANT, Type.SORCERY);
			this.addEffect(Sifter.start().look(3).take(1, spellCards).dumpToBottom().getEventFactory("Look at the top three cards of your library. You may reveal an instant or sorcery card from among them and put it into your hand. Put the rest on the bottom of your library in any order."));
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
