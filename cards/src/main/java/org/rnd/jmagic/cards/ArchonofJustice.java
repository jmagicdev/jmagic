package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Archon of Justice")
@Types({Type.CREATURE})
@SubTypes({SubType.ARCHON})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.RARE), @Printings.Printed(ex = Eventide.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class ArchonofJustice extends Card
{
	public static final class ArchonofJusticeAbility1 extends EventTriggeredAbility
	{
		public ArchonofJusticeAbility1(GameState state)
		{
			super(state, "When Archon of Justice dies, exile target permanent.");
			this.addPattern(whenThisDies());
			SetGenerator target = targetedBy(this.addTarget(Permanents.instance(), "target permanent"));
			this.addEffect(exile(target, "Exile target permanent."));
		}
	}

	public ArchonofJustice(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Archon of Justice dies, exile target permanent.
		this.addAbility(new ArchonofJusticeAbility1(state));
	}
}
