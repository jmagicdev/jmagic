package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cinder Elemental")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MERCADIAN_MASQUES, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class CinderElemental extends Card
{
	public static final class CinderElementalAbility0 extends ActivatedAbility
	{
		public CinderElementalAbility0(GameState state)
		{
			super(state, "(X)(R), (T), Sacrifice Cinder Elemental: Cinder Elemental deals X damage to target creature or player.");
			this.setManaCost(new ManaPool("(X)(R)"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Cinder Elemental"));

			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(ValueOfX.instance(This.instance()), target, "Cinder Elemental deals X damage to target creature or player."));
		}
	}

	public CinderElemental(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (X)(R), (T), Sacrifice Cinder Elemental: Cinder Elemental deals X
		// damage to target creature or player.
		this.addAbility(new CinderElementalAbility0(state));
	}
}
