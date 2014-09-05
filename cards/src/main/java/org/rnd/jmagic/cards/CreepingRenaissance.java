package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Creeping Renaissance")
@Types({Type.SORCERY})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class CreepingRenaissance extends Card
{
	public static final PlayerInterface.ChooseReason CHOOSE_REASON = new PlayerInterface.ChooseReason("Creeping Renaissance", "Choose a permanent type", true);

	public CreepingRenaissance(GameState state)
	{
		super(state);

		// Choose a permanent type. Return all cards of the chosen type from
		// your graveyard to your hand.
		EventFactory choose = new EventFactory(EventType.PLAYER_CHOOSE, "Choose a permanent type.");
		choose.parameters.put(EventType.Parameter.PLAYER, You.instance());
		choose.parameters.put(EventType.Parameter.CHOICE, Identity.fromCollection(Type.permanentTypes()));
		choose.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.ENUM, CHOOSE_REASON));
		this.addEffect(choose);

		EventFactory ret = new EventFactory(EventType.MOVE_OBJECTS, "Return all cards of the chosen type from your graveyard to your hand.");
		ret.parameters.put(EventType.Parameter.CAUSE, This.instance());
		ret.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		ret.parameters.put(EventType.Parameter.OBJECT, HasType.instance(EffectResult.instance(choose)));
		this.addEffect(ret);

		// Flashback (5)(G)(G) (You may cast this card from your graveyard for
		// its flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(5)(G)(G)"));
	}
}
