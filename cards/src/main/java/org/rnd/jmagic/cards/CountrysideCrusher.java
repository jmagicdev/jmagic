package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Countryside Crusher")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.GIANT})
@ManaCost("1RR")
@Printings({@Printings.Printed(ex = Morningtide.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class CountrysideCrusher extends Card
{
	public static final class CountrysideCrusherAbility0 extends EventTriggeredAbility
	{
		public CountrysideCrusherAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, reveal the top card of your library. If it's a land card, put it into your graveyard and repeat this process.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator yourLibrary = LibraryOf.instance(You.instance());
			SetGenerator toReveal = TopMost.instance(yourLibrary, numberGenerator(1), RelativeComplement.instance(InZone.instance(yourLibrary), HasType.instance(Type.LAND)));

			EventFactory reveal = new EventFactory(EventType.REVEAL, "Reveal cards from the top of your library until you reveal a nonland card.");
			reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
			reveal.parameters.put(EventType.Parameter.OBJECT, toReveal);
			this.addEffect(reveal);

			SetGenerator landsRevealed = Intersect.instance(toReveal, HasType.instance(Type.LAND));

			EventFactory pitch = new EventFactory(EventType.PUT_INTO_GRAVEYARD, "Put all revealed nonland cards into your graveyard.");
			pitch.parameters.put(EventType.Parameter.CAUSE, This.instance());
			pitch.parameters.put(EventType.Parameter.OBJECT, landsRevealed);
			this.addEffect(pitch);
		}
	}

	public static final class CountrysideCrusherAbility1 extends EventTriggeredAbility
	{
		public CountrysideCrusherAbility1(GameState state)
		{
			super(state, "Whenever a land card is put into your graveyard from anywhere, put a +1/+1 counter on Countryside Crusher.");

			ZoneChangePattern pitchedLand = new SimpleZoneChangePattern(null, GraveyardOf.instance(You.instance()), HasType.instance(Type.LAND), false);
			this.addPattern(pitchedLand);

			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Countryside Crusher"));
		}
	}

	public CountrysideCrusher(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// At the beginning of your upkeep, reveal the top card of your library.
		// If it's a land card, put it into your graveyard and repeat this
		// process.
		this.addAbility(new CountrysideCrusherAbility0(state));

		// Whenever a land card is put into your graveyard from anywhere, put a
		// +1/+1 counter on Countryside Crusher.
		this.addAbility(new CountrysideCrusherAbility1(state));
	}
}
