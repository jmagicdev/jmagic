package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Flowstone Slide")
@Types({Type.SORCERY})
@ManaCost("X2RR")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.NEMESIS, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class FlowstoneSlide extends Card
{
	public FlowstoneSlide(GameState state)
	{
		super(state);

		SetGenerator X = ValueOfX.instance(This.instance());

		this.addEffect(ptChangeUntilEndOfTurn(CreaturePermanents.instance(), X, Subtract.instance(numberGenerator(0), X), "All creatures get +X/-X until end of turn."));
	}
}
