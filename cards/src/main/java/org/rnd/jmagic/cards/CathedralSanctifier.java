package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Cathedral Sanctifier")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.CLERIC})
@ManaCost("W")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class CathedralSanctifier extends Card
{
	public static final class CathedralSanctifierAbility0 extends EventTriggeredAbility
	{
		public CathedralSanctifierAbility0(GameState state)
		{
			super(state, "When Cathedral Sanctifier enters the battlefield, you gain 3 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), 3, "You gain 3 life."));
		}
	}

	public CathedralSanctifier(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// When Cathedral Sanctifier enters the battlefield, you gain 3 life.
		this.addAbility(new CathedralSanctifierAbility0(state));
	}
}
