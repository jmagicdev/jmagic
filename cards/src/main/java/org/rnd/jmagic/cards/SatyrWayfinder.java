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

			SetGenerator topFour = TopCards.instance(4, LibraryOf.instance(You.instance()));
			this.addEffect(reveal(topFour, "Reveal the top four cards of your library."));

			SetGenerator lands = Intersect.instance(topFour, HasType.instance(Type.LAND));
			EventFactory choose = playerChoose(You.instance(), Between.instance(0, 1), lands, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.PUT_INTO_HAND, "You may put a land card from among them");
			this.addEffect(choose);
			this.addEffect(putIntoHand(EffectResult.instance(choose), You.instance(), "into your hand."));
			this.addEffect(putIntoGraveyard(topFour, "Put the rest into your graveyard."));
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
