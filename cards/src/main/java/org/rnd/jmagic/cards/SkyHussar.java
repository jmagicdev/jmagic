package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Sky Hussar")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.KNIGHT})
@ManaCost("3WU")
@Printings({@Printings.Printed(ex = Dissension.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class SkyHussar extends Card
{
	public static final class ETBUntap extends EventTriggeredAbility
	{
		public ETBUntap(GameState state)
		{
			super(state, "When Sky Hussar enters the battlefield, untap all creatures you control.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(untap(CREATURES_YOU_CONTROL, "Untap all creatures you control."));
		}
	}

	public static final class SkyHussarAbility2 extends org.rnd.jmagic.abilities.keywords.Forecast
	{
		public SkyHussarAbility2(GameState state)
		{
			super(state, "Tap two untapped white and/or blue creatures you control, Reveal Sky Hussar from your hand: Draw a card.");

			SetGenerator tappable = Intersect.instance(Untapped.instance(), CREATURES_YOU_CONTROL, HasColor.instance(Color.WHITE, Color.BLUE));

			EventFactory tap = new EventFactory(EventType.TAP_CHOICE, "Tap two untapped white and/or blue creatures you control");
			tap.parameters.put(EventType.Parameter.CAUSE, This.instance());
			tap.parameters.put(EventType.Parameter.PLAYER, You.instance());
			tap.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			tap.parameters.put(EventType.Parameter.CHOICE, tappable);
			this.addCost(tap);

			this.addEffect(drawACard());
		}
	}

	public SkyHussar(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Sky Hussar enters the battlefield, untap all creatures you
		// control.
		this.addAbility(new ETBUntap(state));

		// Forecast \u2014 Tap two untapped white and/or blue creatures you
		// control, Reveal Sky Hussar from your hand: Draw a card.
		this.addAbility(new SkyHussarAbility2(state));
	}
}
