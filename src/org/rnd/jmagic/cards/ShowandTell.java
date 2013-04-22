package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Show and Tell")
@Types({Type.SORCERY})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class ShowandTell extends Card
{
	public ShowandTell(GameState state)
	{
		super(state);

		// Each player may put an artifact, creature, enchantment, or land card
		// from his or her hand onto the battlefield.
		DynamicEvaluation eachPlayer = DynamicEvaluation.instance();
		SetGenerator thatPlayersStuff = Intersect.instance(InZone.instance(HandOf.instance(eachPlayer)), HasType.instance(Type.ARTIFACT, Type.CREATURE, Type.ENCHANTMENT, Type.LAND));

		EventFactory onePut = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_CHOICE, "Put up to one artifact, creature, enchantment, or land card from your hand onto the battlefield");
		onePut.parameters.put(EventType.Parameter.CAUSE, This.instance());
		onePut.parameters.put(EventType.Parameter.CONTROLLER, eachPlayer);
		onePut.parameters.put(EventType.Parameter.OBJECT, thatPlayersStuff);
		onePut.parameters.put(EventType.Parameter.NUMBER, Between.instance(0, 1));

		EventFactory allPut = new EventFactory(FOR_EACH_PLAYER, "Each player may put an artifact, creature, enchantment, or land card from his or her hand onto the battlefield.");
		allPut.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachPlayer));
		allPut.parameters.put(EventType.Parameter.EFFECT, Identity.instance(onePut));
		this.addEffect(allPut);
	}
}
