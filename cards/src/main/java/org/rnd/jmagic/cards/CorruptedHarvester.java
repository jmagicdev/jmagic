package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Corrupted Harvester")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR})
@ManaCost("4BB")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class CorruptedHarvester extends Card
{
	public static final class CorruptedHarvesterAbility0 extends ActivatedAbility
	{
		public CorruptedHarvesterAbility0(GameState state)
		{
			super(state, "(B), Sacrifice a creature: Regenerate Corrupted Harvester.");
			this.setManaCost(new ManaPool("(B)"));
			this.addCost(sacrificeACreature());
			this.addEffect(regenerate(ABILITY_SOURCE_OF_THIS, "Regenerate Corrupted Harvester."));
		}
	}

	public CorruptedHarvester(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(3);

		// (B), Sacrifice a creature: Regenerate Corrupted Harvester.
		this.addAbility(new CorruptedHarvesterAbility0(state));
	}
}
