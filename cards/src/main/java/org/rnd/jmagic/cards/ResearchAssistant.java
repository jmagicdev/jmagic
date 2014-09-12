package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Research Assistant")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class ResearchAssistant extends Card
{
	public static final class ResearchAssistantAbility0 extends ActivatedAbility
	{
		public ResearchAssistantAbility0(GameState state)
		{
			super(state, "(3)(U), (T): Draw a card, then discard a card.");
			this.setManaCost(new ManaPool("(3)(U)"));
			this.costsTap = true;
			this.addEffect(drawCards(You.instance(), 1, "Draw a card,"));
			this.addEffect(discardCards(You.instance(), 1, "then discard a card."));
		}
	}

	public ResearchAssistant(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// (3)(U), (T): Draw a card, then discard a card.
		this.addAbility(new ResearchAssistantAbility0(state));
	}
}
