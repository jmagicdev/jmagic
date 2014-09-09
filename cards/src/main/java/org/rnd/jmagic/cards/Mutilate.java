package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mutilate")
@Types({Type.SORCERY})
@ManaCost("2BB")
@ColorIdentity({Color.BLACK})
public final class Mutilate extends Card
{
	public Mutilate(GameState state)
	{
		super(state);

		// All creatures get -1/-1 until end of turn for each Swamp you control.
		SetGenerator count = Count.instance(Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.SWAMP)));
		SetGenerator amount = Subtract.instance(numberGenerator(0), count);
		this.addEffect(ptChangeUntilEndOfTurn(CreaturePermanents.instance(), amount, amount, "All creatures get -1/-1 until end of turn for each Swamp you control."));
	}
}
