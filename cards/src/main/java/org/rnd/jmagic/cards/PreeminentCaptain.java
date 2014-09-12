package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Preeminent Captain")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.KITHKIN})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class PreeminentCaptain extends Card
{
	public static final class PreeminentCaptainAbility1 extends EventTriggeredAbility
	{
		public PreeminentCaptainAbility1(GameState state)
		{
			super(state, "Whenever Preeminent Captain attacks, you may put a Soldier creature card from your hand onto the battlefield tapped and attacking.");
			this.addPattern(whenThisAttacks());

			SetGenerator soldiersInHand = Intersect.instance(HasSubType.instance(SubType.SOLDIER), InZone.instance(HandOf.instance(You.instance())));

			EventFactory drop = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_CHOICE, "Put a Soldier creature card from your hand onto the battlefield tapped and attacking.");
			drop.parameters.put(EventType.Parameter.CAUSE, This.instance());
			drop.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			drop.parameters.put(EventType.Parameter.OBJECT, soldiersInHand);
			drop.parameters.put(EventType.Parameter.EFFECT, Identity.instance(EventType.PUT_ONTO_BATTLEFIELD_TAPPED_AND_ATTACKING));
			this.addEffect(youMay(drop));
		}
	}

	public PreeminentCaptain(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// First strike (This creature deals combat damage before creatures
		// without first strike.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		// Whenever Preeminent Captain attacks, you may put a Soldier creature
		// card from your hand onto the battlefield tapped and attacking.
		this.addAbility(new PreeminentCaptainAbility1(state));
	}
}
