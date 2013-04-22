package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spire Barrage")
@Types({Type.SORCERY})
@ManaCost("4R")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class SpireBarrage extends Card
{
	public SpireBarrage(GameState state)
	{
		super(state);

		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
		SetGenerator amount = Count.instance(Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.MOUNTAIN)));
		this.addEffect(spellDealDamage(amount, targetedBy(target), "Spire Barrage deals damage to target creature or player equal to the number of Mountains you control."));
	}
}
