package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Selhoff Occultist")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ROGUE})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class SelhoffOccultist extends Card
{
	public static final class SelhoffOccultistAbility0 extends EventTriggeredAbility
	{
		public SelhoffOccultistAbility0(GameState state)
		{
			super(state, "Whenever Selhoff Occultist or another creature dies, target player puts the top card of his or her library into his or her graveyard.");
			this.addPattern(whenXDies(Union.instance(ABILITY_SOURCE_OF_THIS, CreaturePermanents.instance())));
			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(millCards(target, 1, "Target player puts the top card of his or her library into his or her graveyard."));
		}
	}

	public SelhoffOccultist(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Whenever Selhoff Occultist or another creature dies, target player
		// puts the top card of his or her library into his or her graveyard.
		this.addAbility(new SelhoffOccultistAbility0(state));
	}
}
