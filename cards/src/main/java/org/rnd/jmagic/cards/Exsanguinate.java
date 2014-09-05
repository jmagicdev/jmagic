package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Exsanguinate")
@Types({Type.SORCERY})
@ManaCost("XBB")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class Exsanguinate extends Card
{
	public Exsanguinate(GameState state)
	{
		super(state);

		// Each opponent loses X life.
		EventFactory lose = loseLife(OpponentsOf.instance(You.instance()), ValueOfX.instance(This.instance()), "Each opponent loses X life.");
		this.addEffect(lose);

		// You gain life equal to the life lost this way.
		this.addEffect(gainLife(You.instance(), Sum.instance(EffectResult.instance(lose)), "You gain life equal to the life lost this way."));
	}
}
