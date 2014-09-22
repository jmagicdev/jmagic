package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lavalanche")
@Types({Type.SORCERY})
@ManaCost("XBRG")
@ColorIdentity({Color.BLACK, Color.RED, Color.GREEN})
public final class Lavalanche extends Card
{
	public Lavalanche(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");

		SetGenerator victims = Union.instance(targetedBy(target), Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(targetedBy(target))));
		this.addEffect(spellDealDamage(ValueOfX.instance(This.instance()), victims, "Lavalanche deals X damage to target player and each creature he or she controls."));
	}
}
