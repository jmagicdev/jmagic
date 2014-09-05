package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Corrupt")
@Types({Type.SORCERY})
@ManaCost("5B")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Shadowmoor.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = UrzasSaga.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class Corrupt extends Card
{
	public Corrupt(GameState state)
	{
		super(state);

		// Corrupt deals damage equal to the number of Swamps you control to
		// target creature or player.
		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
		SetGenerator amount = Count.instance(Intersect.instance(HasSubType.instance(SubType.SWAMP), ControlledBy.instance(You.instance())));
		EventFactory damage = spellDealDamage(amount, target, "Corrupt deals damage equal to the number of Swamps you control to target creature or player.");
		this.addEffect(damage);

		// You gain life equal to the damage dealt this way.
		SetGenerator damageDealt = Count.instance(EffectDamage.instance(damage));
		this.addEffect(gainLife(You.instance(), damageDealt, "You gain life equal to the damage dealt this way."));
	}
}
