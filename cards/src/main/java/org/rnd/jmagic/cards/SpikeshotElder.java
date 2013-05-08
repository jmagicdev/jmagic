package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spikeshot Elder")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.SHAMAN})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class SpikeshotElder extends Card
{
	public static final class SpikeshotElderAbility0 extends ActivatedAbility
	{
		public SpikeshotElderAbility0(GameState state)
		{
			super(state, "(1)(R)(R): Spikeshot Elder deals damage equal to its power to target creature or player.");
			this.setManaCost(new ManaPool("(1)(R)(R)"));
			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(PowerOf.instance(ABILITY_SOURCE_OF_THIS), target, "Spikeshot Elder deals damage equal to its power to target creature or player."));
		}
	}

	public SpikeshotElder(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (1)(R)(R): Spikeshot Elder deals damage equal to its power to target
		// creature or player.
		this.addAbility(new SpikeshotElderAbility0(state));
	}
}
