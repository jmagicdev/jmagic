package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sultai Soothsayer")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.NAGA})
@ManaCost("2BGU")
@ColorIdentity({Color.BLUE, Color.BLACK, Color.GREEN})
public final class SultaiSoothsayer extends Card
{
	public static final class SultaiSoothsayerAbility0 extends EventTriggeredAbility
	{
		public SultaiSoothsayerAbility0(GameState state)
		{
			super(state, "When Sultai Soothsayer enters the battlefield, look at the top four cards of your library. Put one of them into your hand and the rest into your graveyard.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator top = TopCards.instance(4, LibraryOf.instance(You.instance()));
			EventFactory look = look(You.instance(), top, "Look at the top four cards of your library.");
			this.addEffect(look);
			SetGenerator them = EffectResult.instance(look);

			EventFactory fromAmongThem = playerChoose(You.instance(), 1, them, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.PUT_INTO_HAND, "Put one of them");
			this.addEffect(fromAmongThem);
			SetGenerator chosen = EffectResult.instance(fromAmongThem);

			this.addEffect(putIntoHand(chosen, You.instance(), "into your hand."));
			this.addEffect(putIntoGraveyard(them, "Put the rest into your graveyard."));
		}
	}

	public SultaiSoothsayer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(5);

		// When Sultai Soothsayer enters the battlefield, look at the top four
		// cards of your library. Put one of them into your hand and the rest
		// into your graveyard.
		this.addAbility(new SultaiSoothsayerAbility0(state));
	}
}
