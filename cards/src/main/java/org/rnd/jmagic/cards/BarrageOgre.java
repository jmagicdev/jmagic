package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Barrage Ogre")
@Types({Type.CREATURE})
@SubTypes({SubType.OGRE, SubType.WARRIOR})
@ManaCost("3RR")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class BarrageOgre extends Card
{
	public static final class BarrageOgreAbility0 extends ActivatedAbility
	{
		public BarrageOgreAbility0(GameState state)
		{
			super(state, "(T), Sacrifice an artifact: Barrage Ogre deals 2 damage to target creature or player.");
			this.costsTap = true;
			this.addCost(sacrifice(You.instance(), 1, ArtifactPermanents.instance(), "Sacrifice an artifact"));
			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(2, target, "Barrage Ogre deals 2 damage to target creature or player."));
		}
	}

	public BarrageOgre(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// (T), Sacrifice an artifact: Barrage Ogre deals 2 damage to target
		// creature or player.
		this.addAbility(new BarrageOgreAbility0(state));
	}
}
