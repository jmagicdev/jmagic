package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Murder of Crows")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("3UU")
@ColorIdentity({Color.BLUE})
public final class MurderofCrows extends Card
{
	public static final class MurderofCrowsAbility1 extends EventTriggeredAbility
	{
		public MurderofCrowsAbility1(GameState state)
		{
			super(state, "Whenever another creature dies, you may draw a card. If you do, discard a card.");
			this.addPattern(whenAnotherCreatureDies());
			this.addEffect(youMayDrawACardIfYouDoDiscardACard());
		}
	}

	public MurderofCrows(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever another creature dies, you may draw a card. If you do,
		// discard a card.
		this.addAbility(new MurderofCrowsAbility1(state));
	}
}
