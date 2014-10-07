package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Satyr Wayfinder")
@Types({Type.CREATURE})
@SubTypes({SubType.SATYR})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class SatyrWayfinder extends Card
{
	public static final class SatyrWayfinderAbility0 extends EventTriggeredAbility
	{
		public SatyrWayfinderAbility0(GameState state)
		{
			super(state, "When Satyr Wayfinder enters the battlefield, reveal the top four cards of your library. You may put a land card from among them into your hand. Put the rest into your graveyard.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(Sifter.start().reveal(4).take(1, HasType.instance(Type.LAND)).dumpToGraveyard().getEventFactory("Reveal the top four cards of your library. You may put a land card from among them into your hand. Put the rest into your graveyard."));
		}
	}

	public SatyrWayfinder(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// When Satyr Wayfinder enters the battlefield, reveal the top four
		// cards of your library. You may put a land card from among them into
		// your hand. Put the rest into your graveyard.
		this.addAbility(new SatyrWayfinderAbility0(state));
	}
}
