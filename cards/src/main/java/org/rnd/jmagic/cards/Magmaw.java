package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Magmaw")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("3RR")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class Magmaw extends Card
{
	public static final class MagmawAbility0 extends ActivatedAbility
	{
		public MagmawAbility0(GameState state)
		{
			super(state, "(1), Sacrifice a nonland permanent: Magmaw deals 1 damage to target creature or player.");
			this.setManaCost(new ManaPool("(1)"));
			this.addCost(sacrifice(You.instance(), 1, RelativeComplement.instance(Permanents.instance(), LandPermanents.instance()), "Sacrifice a nonland permanent"));

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

			this.addEffect(permanentDealDamage(1, targetedBy(target), "Magmaw deals 1 damage to target creature or player."));
		}
	}

	public Magmaw(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// (1), Sacrifice a nonland permanent: Magmaw deals 1 damage to target
		// creature or player.
		this.addAbility(new MagmawAbility0(state));
	}
}
