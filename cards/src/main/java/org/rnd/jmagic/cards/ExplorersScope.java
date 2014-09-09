package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Explorer's Scope")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("1")
@ColorIdentity({})
public final class ExplorersScope extends Card
{
	public static final class Explore extends EventTriggeredAbility
	{
		public Explore(GameState state)
		{
			super(state, "Whenever equipped creature attacks, look at the top card of your library. If it's a land card, you may put it onto the battlefield tapped.");

			SimpleEventPattern whenEquippedCreatureAttacks = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			whenEquippedCreatureAttacks.put(EventType.Parameter.OBJECT, EquippedBy.instance(ABILITY_SOURCE_OF_THIS));
			this.addPattern(whenEquippedCreatureAttacks);

			SetGenerator topCard = TopCards.instance(1, LibraryOf.instance(You.instance()));

			EventFactory look = new EventFactory(EventType.LOOK, "Look at the top card of your library.");
			look.parameters.put(EventType.Parameter.CAUSE, This.instance());
			look.parameters.put(EventType.Parameter.PLAYER, You.instance());
			look.parameters.put(EventType.Parameter.OBJECT, topCard);
			this.addEffect(look);

			EventFactory put = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_TAPPED, "Put it onto the battlefield tapped");
			put.parameters.put(EventType.Parameter.CAUSE, This.instance());
			put.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			put.parameters.put(EventType.Parameter.OBJECT, topCard);

			EventFactory mayPut = youMay(put, "You may put it onto the battlefield tapped");

			EventFactory ifLandThenMayPut = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If it's a land card, you may put it onto the battlefield tapped.");
			ifLandThenMayPut.parameters.put(EventType.Parameter.IF, Intersect.instance(HasType.instance(Type.LAND), topCard));
			ifLandThenMayPut.parameters.put(EventType.Parameter.THEN, Identity.instance(mayPut));
			this.addEffect(ifLandThenMayPut);
		}
	}

	public ExplorersScope(GameState state)
	{
		super(state);

		// Whenever equipped creature attacks, look at the top card of your
		// library. If it's a land card, you may put it onto the battlefield
		// tapped.
		this.addAbility(new Explore(state));

		// Equip (1) ((1): Attach to target creature you control. Equip only as
		// a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(1)"));
	}
}
