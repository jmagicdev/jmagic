package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Lavalanche")
@Types({Type.SORCERY})
@ManaCost("XBRG")
@Printings({@Printings.Printed(ex = AlaraReborn.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.BLACK, Color.RED})
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
