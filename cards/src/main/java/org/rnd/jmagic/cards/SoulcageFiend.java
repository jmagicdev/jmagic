package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Soulcage Fiend")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class SoulcageFiend extends Card
{
	public static final class SoulcageFiendAbility0 extends EventTriggeredAbility
	{
		public SoulcageFiendAbility0(GameState state)
		{
			super(state, "When Soulcage Fiend dies, each player loses 3 life.");
			this.addPattern(whenThisDies());
			this.addEffect(loseLife(Players.instance(), 3, "Each player loses 3 life."));
		}
	}

	public SoulcageFiend(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// When Soulcage Fiend dies, each player loses 3 life.
		this.addAbility(new SoulcageFiendAbility0(state));
	}
}
