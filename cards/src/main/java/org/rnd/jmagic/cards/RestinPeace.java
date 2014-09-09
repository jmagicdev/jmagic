package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Rest in Peace")
@Types({Type.ENCHANTMENT})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class RestinPeace extends Card
{
	public static final class RestinPeaceAbility0 extends EventTriggeredAbility
	{
		public RestinPeaceAbility0(GameState state)
		{
			super(state, "When Rest in Peace enters the battlefield, exile all cards from all graveyards.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(exile(InZone.instance(GraveyardOf.instance(Players.instance())), "exile all cards from all graveyards."));
		}
	}

	public static final class RestinPeaceAbility1 extends StaticAbility
	{
		public RestinPeaceAbility1(GameState state)
		{
			super(state, "If a card or token would be put into a graveyard from anywhere, exile it instead.");

			SetGenerator graveyards = GraveyardOf.instance(Players.instance());

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(state.game, "If a card or token would be put into a graveyard from anywhere, exile it instead");
			replacement.addPattern(new SimpleZoneChangePattern(null, graveyards, null, true));
			replacement.changeDestination(ExileZone.instance());

			this.addEffectPart(replacementEffectPart(replacement));
		}
	}

	public RestinPeace(GameState state)
	{
		super(state);

		// When Rest in Peace enters the battlefield, exile all cards from all
		// graveyards.
		this.addAbility(new RestinPeaceAbility0(state));

		// If a card or token would be put into a graveyard from anywhere, exile
		// it instead.
		this.addAbility(new RestinPeaceAbility1(state));
	}
}
