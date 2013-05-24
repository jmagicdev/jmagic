package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Blood Artist")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class BloodArtist extends Card
{
	public static final class BloodArtistAbility0 extends EventTriggeredAbility
	{
		public BloodArtistAbility0(GameState state)
		{
			super(state, "Whenever Blood Artist or another creature dies, target player loses 1 life and you gain 1 life.");
			this.addPattern(whenXDies(CreaturePermanents.instance()));

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(loseLife(target, 1, "Target player loses 1 life"));
			this.addEffect(gainLife(You.instance(), 1, "and you gain 1 life."));
		}
	}

	public BloodArtist(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		// Whenever Blood Artist or another creature dies, target player loses 1
		// life and you gain 1 life.
		this.addAbility(new BloodArtistAbility0(state));
	}
}
