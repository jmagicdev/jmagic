package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chorus of Might")
@Types({Type.INSTANT})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class ChorusofMight extends Card
{
	public ChorusofMight(GameState state)
	{
		super(state);

		// Until end of turn, target creature gets +1/+1 for each creature you
		// control and gains trample.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		SetGenerator num = Count.instance(CREATURES_YOU_CONTROL);
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, num, num, "Target creature gets +1/+1 for each creature you control and gains trample.", org.rnd.jmagic.abilities.keywords.Trample.class));
	}
}
