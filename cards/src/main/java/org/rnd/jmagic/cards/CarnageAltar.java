package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Carnage Altar")
@Types({Type.ARTIFACT})
@ManaCost("2")
@ColorIdentity({})
public final class CarnageAltar extends Card
{
	public static final class SacDraw extends ActivatedAbility
	{
		public SacDraw(GameState state)
		{
			super(state, "(3), Sacrifice a creature: Draw a card.");
			this.setManaCost(new ManaPool("3"));
			this.addCost(sacrificeACreature());
			this.addEffect(drawACard());
		}
	}

	public CarnageAltar(GameState state)
	{
		super(state);

		// (3), Sacrifice a creature: Draw a card.
		this.addAbility(new SacDraw(state));
	}
}
