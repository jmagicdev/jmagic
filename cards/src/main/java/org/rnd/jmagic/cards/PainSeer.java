package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pain Seer")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class PainSeer extends Card
{
	public static final class PainSeerAbility0 extends EventTriggeredAbility
	{
		public PainSeerAbility0(GameState state)
		{
			super(state, "Inspired \u2014 Whenever Pain Seer becomes untapped, reveal the top card of your library and put that card into your hand. You lose life equal to that card's converted mana cost.");
			this.addPattern(inspired());

			SetGenerator library = LibraryOf.instance(You.instance());
			SetGenerator topCard = TopCards.instance(numberGenerator(1), library);

			EventFactory reveal = reveal(topCard, "Reveal the top card of your library");
			this.addEffect(reveal);
			this.addEffect(putIntoHand(topCard, OwnerOf.instance(topCard), "and put that card into your hand."));
			this.addEffect(loseLife(You.instance(), ConvertedManaCostOf.instance(EffectResult.instance(reveal)), "You lose life equal to its converted mana cost."));
		}
	}

	public PainSeer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Inspired \u2014 Whenever Pain Seer becomes untapped, reveal the top
		// card of your library and put that card into your hand. You lose life
		// equal to that card's converted mana cost.
		this.addAbility(new PainSeerAbility0(state));
	}
}
