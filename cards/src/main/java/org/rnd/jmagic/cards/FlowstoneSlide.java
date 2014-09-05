package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Flowstone Slide")
@Types({Type.SORCERY})
@ManaCost("X2RR")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = Nemesis.class, r = Rarity.RARE)})
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
