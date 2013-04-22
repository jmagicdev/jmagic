package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Floating-Dream Zubera")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT, SubType.ZUBERA})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.CHAMPIONS_OF_KAMIGAWA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class FloatingDreamZubera extends Card
{
	public static final class DeathDraw extends EventTriggeredAbility
	{
		public DeathDraw(GameState state)
		{
			super(state, "When Floating-Dream Zubera dies, draw a card for each Zubera put into a graveyard from the battlefield this turn.");
			this.addPattern(whenThisDies());

			state.ensureTracker(new ZuberasPutIntoGraveyardsFromBattlefieldThisTurn.Tracker());
			SetGenerator deadZuberas = ZuberasPutIntoGraveyardsFromBattlefieldThisTurn.instance();
			this.addEffect(drawCards(You.instance(), deadZuberas, "Draw a card for each Zubera put into a graveyard from the battlefield this turn."));
		}
	}

	public FloatingDreamZubera(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// When Floating-Dream Zubera is put into a graveyard from the
		// battlefield, draw a card for each Zubera put into a graveyard from
		// the battlefield this turn.
		this.addAbility(new DeathDraw(state));
	}
}
