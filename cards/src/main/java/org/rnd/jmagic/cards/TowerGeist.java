package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Tower Geist")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class TowerGeist extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("Tower Geist", "Choose a card to put into your hand", false);

	public static final class TowerGeistAbility1 extends EventTriggeredAbility
	{
		public TowerGeistAbility1(GameState state)
		{
			super(state, "When Tower Geist enters the battlefield, look at the top two cards of your library. Put one of them into your hand and the other into your graveyard.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory look = look(You.instance(), TopCards.instance(2, LibraryOf.instance(You.instance())), "Look at the top two cards of your library");
			this.addEffect(look);

			SetGenerator looked = EffectResult.instance(look);

			EventFactory choose = playerChoose(You.instance(), 1, looked, PlayerInterface.ChoiceType.OBJECTS, REASON, "Choose a card to put into your hand");
			this.addEffect(choose);

			SetGenerator chosen = EffectResult.instance(choose);

			EventFactory putIntoHand = putIntoHand(chosen, You.instance(), "Put one of them into your hand");
			EventFactory putIntoGraveyard = putIntoGraveyard(RelativeComplement.instance(looked, chosen), "and the other into your graveyard.");
			this.addEffect(simultaneous(putIntoHand, putIntoGraveyard));
		}
	}

	public TowerGeist(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Tower Geist enters the battlefield, look at the top two cards of
		// your library. Put one of them into your hand and the other into your
		// graveyard.
		this.addAbility(new TowerGeistAbility1(state));
	}
}
