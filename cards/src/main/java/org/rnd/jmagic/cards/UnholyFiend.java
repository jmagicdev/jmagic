package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Unholy Fiend")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR})
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class UnholyFiend extends AlternateCard
{
	public static final class UnholyFiendAbility0 extends EventTriggeredAbility
	{
		public UnholyFiendAbility0(GameState state)
		{
			super(state, "At the beginning of your end step, you lose 1 life.");
			this.addPattern(atTheBeginningOfYourEndStep());

			this.addEffect(loseLife(You.instance(), 1, "You lose 1 life."));
		}
	}

	public UnholyFiend(GameState state)
	{
		super(state);
		this.setColorIndicator(Color.BLACK);

		this.setPower(3);
		this.setToughness(3);

		// At the beginning of your end step, you lose 1 life.
		this.addAbility(new UnholyFiendAbility0(state));
	}
}
