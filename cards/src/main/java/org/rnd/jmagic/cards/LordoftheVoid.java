package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lord of the Void")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("4BBB")
@ColorIdentity({Color.BLACK})
public final class LordoftheVoid extends Card
{
	public static final class LordoftheVoidAbility1 extends EventTriggeredAbility
	{
		public LordoftheVoidAbility1(GameState state)
		{
			super(state, "Whenever Lord of the Void deals combat damage to a player, exile the top seven cards of that player's library, then put a creature card from among them onto the battlefield under your control.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());

			SetGenerator thatPlayer = TakerOfDamage.instance(TriggerDamage.instance(This.instance()));

			EventFactory exile = exile(TopCards.instance(7, LibraryOf.instance(thatPlayer)), "Exile the top seven cards of that player's library,");
			this.addEffect(exile);

			EventFactory move = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_CHOICE, "then put a creature card from among them onto the battlefield under your control.");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			move.parameters.put(EventType.Parameter.OBJECT, Intersect.instance(HasType.instance(Type.CREATURE), NewObjectOf.instance(EffectResult.instance(exile))));
			this.addEffect(move);
		}
	}

	public LordoftheVoid(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Lord of the Void deals combat damage to a player, exile the
		// top seven cards of that player's library, then put a creature card
		// from among them onto the battlefield under your control.
		this.addAbility(new LordoftheVoidAbility1(state));
	}
}
