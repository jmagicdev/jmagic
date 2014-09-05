package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Arbor Elf")
@Types({Type.CREATURE})
@SubTypes({SubType.DRUID, SubType.ELF})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = Worldwake.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class ArborElf extends Card
{
	public static final class UntapForest extends ActivatedAbility
	{
		public UntapForest(GameState state)
		{
			super(state, "(T): Untap target Forest.");
			this.costsTap = true;

			Target target = this.addTarget(Intersect.instance(HasSubType.instance(SubType.FOREST), Permanents.instance()), "target Forest");
			this.addEffect(untap(targetedBy(target), "Untap target Forest."));
		}
	}

	public ArborElf(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): Untap target Forest.
		this.addAbility(new UntapForest(state));
	}
}
