package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Ember Hauler")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN})
@ManaCost("RR")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class EmberHauler extends Card
{
	public static final class EmberHaulerAbility0 extends ActivatedAbility
	{
		public EmberHaulerAbility0(GameState state)
		{
			super(state, "(1), Sacrifice Ember Hauler: Ember Hauler deals 2 damage to target creature or player.");
			this.setManaCost(new ManaPool("(1)"));
			this.addCost(sacrificeThis("Ember Hauler"));
			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(2, target, "Ember Hauler deals 2 damage to target creature or player."));
		}
	}

	public EmberHauler(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (1), Sacrifice Ember Hauler: Ember Hauler deals 2 damage to target
		// creature or player.
		this.addAbility(new EmberHaulerAbility0(state));
	}
}
