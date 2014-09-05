package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Gutter Grime")
@Types({Type.ENCHANTMENT})
@ManaCost("4G")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class GutterGrime extends Card
{
	public static final class GutterGrimeAbility0 extends EventTriggeredAbility
	{
		public static final class PowerToughnessSlimeCounters extends StaticAbility
		{
			public PowerToughnessSlimeCounters(GameState state)
			{
				super(state, "This creature's power and toughness are each equal to the number of slime counters on Gutter Grime.");

				SetGenerator gutterGrime = AbilitySource.instance(TokenSource.instance(This.instance()));
				SetGenerator amount = Count.instance(CountersOn.instance(gutterGrime, Counter.CounterType.SLIME));
				this.addEffectPart(modifyPowerAndToughness(This.instance(), amount, amount));

				state.ensureTracker(new TokenSource.Tracker());
			}
		}

		public GutterGrimeAbility0(GameState state)
		{
			super(state, "Whenever a nontoken creature you control dies, put a slime counter on Gutter Grime, then put a green Ooze creature token onto the battlefield with \"This creature's power and toughness are each equal to the number of slime counters on Gutter Grime.\"");
			SetGenerator nonTokenCreaturesYouControl = Intersect.instance(NonToken.instance(), CREATURES_YOU_CONTROL);
			this.addPattern(new SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), nonTokenCreaturesYouControl, true));

			this.addEffect(putCounters(1, Counter.CounterType.SLIME, ABILITY_SOURCE_OF_THIS, "Put a slime counter on Gutter Grime,"));

			CreateTokensFactory token = new CreateTokensFactory(1, 0, 0, "then put a green Ooze creature token onto the battlefield with \"This creature's power and toughness are each equal to the number of slime counters on Gutter Grime.\"");
			token.setColors(Color.GREEN);
			token.setSubTypes(SubType.OOZE);
			token.addAbility(PowerToughnessSlimeCounters.class);
			this.addEffect(token.getEventFactory());
		}
	}

	public GutterGrime(GameState state)
	{
		super(state);

		// Whenever a nontoken creature you control dies, put a slime counter on
		// Gutter Grime, then put a green Ooze creature token onto the
		// battlefield with
		// "This creature's power and toughness are each equal to the number of slime counters on Gutter Grime."
		this.addAbility(new GutterGrimeAbility0(state));
	}
}
