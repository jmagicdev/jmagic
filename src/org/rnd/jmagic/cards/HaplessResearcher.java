package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hapless Researcher")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.JUDGMENT, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class HaplessResearcher extends Card
{
	public static final class HaplessResearcherAbility0 extends ActivatedAbility
	{
		public HaplessResearcherAbility0(GameState state)
		{
			super(state, "Sacrifice Hapless Researcher: Draw a card, then discard a card.");
			this.addCost(sacrificeThis("Hapless Researcher"));
			this.addEffect(drawCards(You.instance(), 1, "Draw a card,"));
			this.addEffect(discardCards(You.instance(), 1, "then discard a card."));
		}
	}

	public HaplessResearcher(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Sacrifice Hapless Researcher: Draw a card, then discard a card.
		this.addAbility(new HaplessResearcherAbility0(state));
	}
}
