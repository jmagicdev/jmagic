package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sultai Ascendancy")
@Types({Type.ENCHANTMENT})
@ManaCost("BGU")
@ColorIdentity({Color.BLUE, Color.BLACK, Color.GREEN})
public final class SultaiAscendancy extends Card
{
	public static final class SultaiAscendancyAbility0 extends EventTriggeredAbility
	{
		public SultaiAscendancyAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, look at the top two cards of your library. Put any number of them into your graveyard and the rest back on top of your library in any order.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator top = TopCards.instance(2, LibraryOf.instance(You.instance()));
			EventFactory look = look(You.instance(), top, "Look at the top two cards of your library.");
			this.addEffect(look);
			SetGenerator them = EffectResult.instance(look);

			EventFactory choose = playerChoose(You.instance(), Between.instance(0, null), them, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.DISCARD, "Put any number of them");
			this.addEffect(choose);
			SetGenerator chosen = EffectResult.instance(choose);

			this.addEffect(putIntoGraveyard(chosen, "into your graveyard"));
			this.addEffect(putOnTopOfLibrary(them, "and the rest back on top of your library in any order."));
		}
	}

	public SultaiAscendancy(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, look at the top two cards of your
		// library. Put any number of them into your graveyard and the rest back
		// on top of your library in any order.
		this.addAbility(new SultaiAscendancyAbility0(state));
	}
}
