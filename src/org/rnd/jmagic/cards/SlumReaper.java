package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Slum Reaper")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class SlumReaper extends Card
{
	public static final class SlumReaperAbility0 extends EventTriggeredAbility
	{
		public SlumReaperAbility0(GameState state)
		{
			super(state, "When Slum Reaper enters the battlefield, each player sacrifices a creature.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(sacrifice(Players.instance(), 1, CreaturePermanents.instance(), "Each player sacrifices a creature."));
		}
	}

	public SlumReaper(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(2);

		// When Slum Reaper enters the battlefield, each player sacrifices a
		// creature.
		this.addAbility(new SlumReaperAbility0(state));
	}
}
