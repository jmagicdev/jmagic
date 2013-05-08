package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chaos Charm")
@Types({Type.INSTANT})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.MIRAGE, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class ChaosCharm extends Card
{
	public ChaosCharm(GameState state)
	{
		super(state);

		// Mode 1 - Destroy target Wall
		{
			Target target = this.addTarget(1, Intersect.instance(InZone.instance(Battlefield.instance()), HasSubType.instance(SubType.WALL)), "target Wall");
			this.addEffect(1, destroy(targetedBy(target), "Destroy target Wall"));
		}

		// Mode 2 - Chaos Charm deals 1 damage to target creature
		{
			Target target = this.addTarget(2, CreaturePermanents.instance(), "target creature");
			this.addEffect(2, spellDealDamage(1, targetedBy(target), "Chaos Charm deals 1 damage to target creature"));
		}

		// Mode 3 - Target creature gains Haste until end of turn
		{
			Target target = this.addTarget(3, CreaturePermanents.instance(), "target creature");
			this.addEffect(3, addAbilityUntilEndOfTurn(targetedBy(target), org.rnd.jmagic.abilities.keywords.Haste.class, "target creature gains haste until end of turn."));
		}
	}
}
