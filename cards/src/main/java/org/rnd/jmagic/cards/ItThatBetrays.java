package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("It That Betrays")
@Types({Type.CREATURE})
@SubTypes({SubType.ELDRAZI})
@ManaCost("(12)")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class ItThatBetrays extends Card
{
	public static final class Betrayal extends EventTriggeredAbility
	{
		public Betrayal(GameState state)
		{
			super(state, "Whenever an opponent sacrifices a nontoken permanent, put that card onto the battlefield under your control.");

			SetGenerator nonTokenPermanents = RelativeComplement.instance(Permanents.instance(), Tokens.instance());

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.SACRIFICE_ONE_PERMANENT);
			pattern.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
			pattern.put(EventType.Parameter.PERMANENT, new ZoneChangeContaining(nonTokenPermanents));
			this.addPattern(pattern);

			SetGenerator triggerEvent = TriggerEvent.instance(This.instance());
			SetGenerator sacrifice = EventResult.instance(triggerEvent);

			EventFactory addMana = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Put that card onto the battlefield under your control.");
			addMana.parameters.put(EventType.Parameter.CAUSE, This.instance());
			addMana.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			addMana.parameters.put(EventType.Parameter.OBJECT, NewObjectOf.instance(sacrifice));
			this.addEffect(addMana);
		}
	}

	public ItThatBetrays(GameState state)
	{
		super(state);

		this.setPower(11);
		this.setToughness(11);

		// Annihilator 2 (Whenever this creature attacks, defending player
		// sacrifices two permanents.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Annihilator.Final(state, 2));

		// Whenever an opponent sacrifices a nontoken permanent, put that card
		// onto the battlefield under your control.
		this.addAbility(new Betrayal(state));
	}
}
