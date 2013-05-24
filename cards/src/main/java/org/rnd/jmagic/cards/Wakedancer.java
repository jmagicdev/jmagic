package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wakedancer")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.HUMAN})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class Wakedancer extends Card
{
	public static final class WakedancerAbility0 extends EventTriggeredAbility
	{
		public WakedancerAbility0(GameState state)
		{
			super(state, "Morbid \u2014 When Wakedancer enters the battlefield, if a creature died this turn, put a 2/2 black Zombie creature token onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.interveningIf = Morbid.instance();
			state.ensureTracker(new Morbid.Tracker());

			CreateTokensFactory tokens = new CreateTokensFactory(1, 2, 2, "Put a 2/2 black Zombie creature token onto the battlefield.");
			tokens.setColors(Color.BLACK);
			tokens.setSubTypes(SubType.ZOMBIE);
			this.addEffect(tokens.getEventFactory());
		}
	}

	public Wakedancer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Morbid \u2014 When Wakedancer enters the battlefield, if a creature
		// died this turn, put a 2/2 black Zombie creature token onto the
		// battlefield.
		this.addAbility(new WakedancerAbility0(state));
	}
}
