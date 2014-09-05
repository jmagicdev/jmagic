package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Eyeblight's Ending")
@Types({Type.INSTANT, Type.TRIBAL})
@SubTypes({SubType.ELF})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Lorwyn.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class EyeblightsEnding extends Card
{
	public EyeblightsEnding(GameState state)
	{
		super(state);

		// Destroy target non-Elf creature.
		Target target = this.addTarget(RelativeComplement.instance(CreaturePermanents.instance(), HasSubType.instance(SubType.ELF)), "target non-Elf creature");
		this.addEffect(destroy(targetedBy(target), "Destroy target non-Elf creature."));
	}
}
