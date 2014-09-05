package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ulvenwald Tracker")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SHAMAN})
@ManaCost("G")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class UlvenwaldTracker extends Card
{
	public static final class UlvenwaldTrackerAbility0 extends ActivatedAbility
	{
		public UlvenwaldTrackerAbility0(GameState state)
		{
			super(state, "(1)(G), (T): Target creature you control fights another target creature.");
			this.setManaCost(new ManaPool("(1)(G)"));
			this.costsTap = true;

			SetGenerator yourGuy = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));
			SetGenerator theirGuy = targetedBy(this.addTarget(RelativeComplement.instance(CreaturePermanents.instance(), yourGuy), "another target creature"));
			this.addEffect(fight(Union.instance(yourGuy, theirGuy), "Target creature you control fights another target creature."));
		}
	}

	public UlvenwaldTracker(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (1)(G), (T): Target creature you control fights another target
		// creature. (Each deals damage equal to its power to the other.)
		this.addAbility(new UlvenwaldTrackerAbility0(state));
	}
}
