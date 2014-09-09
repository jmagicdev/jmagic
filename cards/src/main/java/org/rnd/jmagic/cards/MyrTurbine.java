package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Myr Turbine")
@Types({Type.ARTIFACT})
@ManaCost("5")
@ColorIdentity({})
public final class MyrTurbine extends Card
{
	public static final class MyrTurbineAbility0 extends ActivatedAbility
	{
		public MyrTurbineAbility0(GameState state)
		{
			super(state, "(T): Put a 1/1 colorless Myr artifact creature token onto the battlefield.");
			this.costsTap = true;

			CreateTokensFactory factory = new CreateTokensFactory(1, 1, 1, "Put a 1/1 colorless Myr artifact creature token onto the battlefield.");
			factory.setSubTypes(SubType.MYR);
			factory.setArtifact();
			this.addEffect(factory.getEventFactory());
		}
	}

	public static final class MyrTurbineAbility1 extends ActivatedAbility
	{
		public MyrTurbineAbility1(GameState state)
		{
			super(state, "(T), Tap five untapped Myr you control: Search your library for a Myr creature card, put it onto the battlefield, then shuffle your library.");
			this.costsTap = true;
			// Tap five untapped Myr you control
			EventFactory cost = new EventFactory(EventType.TAP_CHOICE, "Tap five untapped Myr you control");
			cost.parameters.put(EventType.Parameter.CAUSE, This.instance());
			cost.parameters.put(EventType.Parameter.PLAYER, You.instance());
			cost.parameters.put(EventType.Parameter.CHOICE, Identity.instance(Intersect.instance(Untapped.instance(), CREATURES_YOU_CONTROL, HasSubType.instance(SubType.MYR))));
			cost.parameters.put(EventType.Parameter.NUMBER, numberGenerator(5));
			this.addCost(cost);

			EventFactory factory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a Myr creature card, put it onto the battlefield, then shuffle your library.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			factory.parameters.put(EventType.Parameter.TO, Battlefield.instance());
			factory.parameters.put(EventType.Parameter.TYPE, HasSubType.instance(SubType.MYR));
			this.addEffect(factory);
		}
	}

	public MyrTurbine(GameState state)
	{
		super(state);

		// (T): Put a 1/1 colorless Myr artifact creature token onto the
		// battlefield.
		this.addAbility(new MyrTurbineAbility0(state));

		// (T), Tap five untapped Myr you control: Search your library for a Myr
		// creature card, put it onto the battlefield, then shuffle your
		// library.
		this.addAbility(new MyrTurbineAbility1(state));
	}
}
