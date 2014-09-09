package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Skirsdag High Priest")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.HUMAN})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class SkirsdagHighPriest extends Card
{
	public static final class SkirsdagHighPriestAbility0 extends ActivatedAbility
	{
		public SkirsdagHighPriestAbility0(GameState state)
		{
			super(state, "(T), Tap two untapped creatures you control: Put a 5/5 black Demon creature token with flying onto the battlefield. Activate this ability only if a creature died this turn.");
			this.costsTap = true;

			// Tap two untapped creatures you control
			EventFactory cost = new EventFactory(EventType.TAP_CHOICE, "Tap two untapped creatures you control");
			cost.parameters.put(EventType.Parameter.CAUSE, This.instance());
			cost.parameters.put(EventType.Parameter.PLAYER, You.instance());
			cost.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(ControlledBy.instance(You.instance()), HasType.instance(Type.CREATURE), Untapped.instance()));
			cost.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			this.addCost(cost);

			CreateTokensFactory factory = new CreateTokensFactory(1, 5, 5, "Put a 5/5 black Demon creature token with flying onto the battlefield.");
			factory.setColors(Color.BLACK);
			factory.setSubTypes(SubType.DEMON);
			factory.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(factory.getEventFactory());

			this.addActivateRestriction(Not.instance(Morbid.instance()));

			state.ensureTracker(new Morbid.Tracker());
		}
	}

	public SkirsdagHighPriest(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Morbid \u2014 (T), Tap two untapped creatures you control: Put a 5/5
		// black Demon creature token with flying onto the battlefield. Activate
		// this ability only if a creature died this turn.
		this.addAbility(new SkirsdagHighPriestAbility0(state));
	}
}
