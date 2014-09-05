package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Devour Flesh")
@Types({Type.INSTANT})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class DevourFlesh extends Card
{
	public DevourFlesh(GameState state)
	{
		super(state);

		// Target player sacrifices a creature, then gains life equal to that
		// creature's toughness.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

		EventFactory sacrifice = sacrifice(target, 1, HasType.instance(Type.CREATURE), "Target player sacrifices a creature,");
		this.addEffect(sacrifice);

		this.addEffect(gainLife(target, ToughnessOf.instance(OldObjectOf.instance(EffectResult.instance(sacrifice))), "then gains life equal to that creature's toughness."));
	}
}
