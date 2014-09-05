package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Leyline of the Void")
@Types({Type.ENCHANTMENT})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.RARE), @Printings.Printed(ex = Guildpact.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class LeylineoftheVoid extends Card
{
	public static final class Void extends StaticAbility
	{
		public Void(GameState state)
		{
			super(state, "If a card would be put into an opponent's graveyard from anywhere, exile it instead.");

			SetGenerator opponentsGraveyard = GraveyardOf.instance(OpponentsOf.instance(You.instance()));

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(state.game, "If a card would be put into an opponent's graveyard from anywhere, exile it instead");
			replacement.addPattern(new SimpleZoneChangePattern(null, opponentsGraveyard, Cards.instance(), true));
			replacement.changeDestination(ExileZone.instance());

			this.addEffectPart(replacementEffectPart(replacement));
		}
	}

	public LeylineoftheVoid(GameState state)
	{
		super(state);

		// If Leyline of the Void is in your opening hand, you may begin the
		// game with it on the battlefield.
		this.addAbility(new org.rnd.jmagic.abilities.LeylineAbility(state, this.getName()));

		// If a card would be put into an opponent's graveyard from anywhere,
		// exile it instead.
		this.addAbility(new Void(state));
	}
}
