package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ill-Gotten Gains")
@Types({Type.SORCERY})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = UrzasSaga.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class IllGottenGains extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("IllGottenGains", "Choose up to three cards in your graveyard.", true);

	public IllGottenGains(GameState state)
	{
		super(state);

		// Exile Ill-Gotten Gains.
		this.addEffect(exile(This.instance(), "Exile Ill-Gotten Gains."));

		// Each player discards his or her hand,
		this.addEffect(discardHand(Players.instance(), "Each player discards his or her hand,"));

		// then returns up to three cards from his or her graveyard to his or
		// her hand.
		SetGenerator eachPlayer = DynamicEvaluation.instance();
		SetGenerator graveyard = GraveyardOf.instance(eachPlayer);
		EventFactory chooseCards = new EventFactory(EventType.PLAYER_CHOOSE, "Choose up to three cards in your graveyard");
		chooseCards.parameters.put(EventType.Parameter.PLAYER, eachPlayer);
		chooseCards.parameters.put(EventType.Parameter.NUMBER, Between.instance(0, 3));
		chooseCards.parameters.put(EventType.Parameter.CHOICE, InZone.instance(graveyard));
		chooseCards.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.OBJECTS, REASON));

		EventFactory eachPlayerChooses = new EventFactory(FOR_EACH_PLAYER, "");
		eachPlayerChooses.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachPlayer));
		eachPlayerChooses.parameters.put(EventType.Parameter.EFFECT, Identity.instance(chooseCards));
		this.addEffect(eachPlayerChooses);

		SetGenerator chosenCards = ForEachResult.instance(eachPlayerChooses, eachPlayer);

		EventFactory returnCards = new EventFactory(EventType.MOVE_OBJECTS, "Return those cards to your hand");
		returnCards.parameters.put(EventType.Parameter.CAUSE, This.instance());
		returnCards.parameters.put(EventType.Parameter.TO, HandOf.instance(eachPlayer));
		returnCards.parameters.put(EventType.Parameter.OBJECT, chosenCards);

		EventFactory effect = new EventFactory(FOR_EACH_PLAYER, "then returns up to three cards from his or her graveyard to his or her hand.");
		effect.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachPlayer));
		effect.parameters.put(EventType.Parameter.EFFECT, Identity.instance(returnCards));
		this.addEffect(effect);
	}
}
