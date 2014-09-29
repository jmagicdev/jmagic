package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Ugin's Nexus")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ARTIFACT})
@ManaCost("5")
@ColorIdentity({})
public final class UginsNexus extends Card
{
	public static final class BeginExtraTurn implements EventPattern
	{

		@Override
		public boolean match(Event event, Identified object, GameState state)
		{
			if(event.type != EventType.BEGIN_TURN)
				return false;

			Turn turn = event.parameters.get(EventType.Parameter.TURN).evaluate(state, object).getOne(Turn.class);
			return turn.extra;
		}

		@Override
		public boolean looksBackInTime()
		{
			return false;
		}

		@Override
		public boolean matchesManaAbilities()
		{
			return false;
		}

	}

	public static final class UginsNexusAbility0 extends StaticAbility
	{
		public UginsNexusAbility0(GameState state)
		{
			super(state, "If a player would begin an extra turn, that player skips that turn instead.");

			EventReplacementEffect skip = new EventReplacementEffect(state.game, "If a player would begin an extra turn, that player skips that turn instead.", new BeginExtraTurn());
			this.addEffectPart(replacementEffectPart(skip));
		}
	}

	public static final class UginsNexusAbility1 extends StaticAbility
	{
		public UginsNexusAbility1(GameState state)
		{
			super(state, "If Ugin's Nexus would be put into a graveyard from the battlefield, instead exile it and take an extra turn after this one.");

			ZoneChangeReplacementEffect extraTurn = new ZoneChangeReplacementEffect(state.game, "If Ugin's Nexus would be put into a graveyard from the battlefield, instead exile it and take an extra turn after this one.");
			extraTurn.addPattern(new SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), This.instance(), true));
			extraTurn.changeDestination(ExileZone.instance());
			extraTurn.addEffect(takeExtraTurns(You.instance(), 1, "Take an extra turn after this one."));
		}
	}

	public UginsNexus(GameState state)
	{
		super(state);

		// If a player would begin an extra turn, that player skips that turn
		// instead.
		this.addAbility(new UginsNexusAbility0(state));

		// If Ugin's Nexus would be put into a graveyard from the battlefield,
		// instead exile it and take an extra turn after this one.
		this.addAbility(new UginsNexusAbility1(state));
	}
}
