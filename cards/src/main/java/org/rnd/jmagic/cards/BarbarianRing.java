package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Barbarian Ring")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Odyssey.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class BarbarianRing extends Card
{
	public static final class TapForShock extends ActivatedAbility
	{
		public TapForShock(GameState state)
		{
			super(state, "(R), (T), Sacrifice Barbarian Ring: Barbarian Ring deals 2 damage to target creature or player. Activate this ability only if seven or more cards are in your graveyard.");
			this.setManaCost(new ManaPool("R"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Barbarian Ring"));

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
			this.addEffect(permanentDealDamage(2, targetedBy(target), ""));
			this.addActivateRestriction(Not.instance(Threshold.instance()));
		}
	}

	public BarbarianRing(GameState state)
	{
		super(state);

		// (T): Add (R) to your mana pool. Barbarian Ring deals 1 damage to you.
		this.addAbility(new org.rnd.jmagic.abilities.TapForManaPain(state, this.getName(), "R"));

		// Threshold \u2014 (R), (T), Sacrifice Barbarian Ring: Barbarian Ring
		// deals 2 damage to target creature or player. Activate this ability
		// only if seven or more cards are in your graveyard.
		this.addAbility(new TapForShock(state));
	}
}
