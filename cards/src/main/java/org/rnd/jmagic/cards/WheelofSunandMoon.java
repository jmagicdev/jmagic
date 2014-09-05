package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Wheel of Sun and Moon")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("(G/W)(G/W)")
@Printings({@Printings.Printed(ex = Shadowmoor.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class WheelofSunandMoon extends Card
{
	public static final class WheelofSunandMoonAbility1 extends StaticAbility
	{
		public WheelofSunandMoonAbility1(GameState state)
		{
			super(state, "If a card would be put into enchanted player's graveyard from anywhere, instead that card is revealed and put on the bottom of that player's library.");

			SetGenerator enchantedPlayer = EnchantedBy.instance(This.instance());

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(state.game, "If a card would be put into enchanted player's graveyard from anywhere, instead that card is revealed and put on the bottom of that player's library.");
			replacement.addPattern(new SimpleZoneChangePattern(null, GraveyardOf.instance(enchantedPlayer), Cards.instance(), false));

			SetGenerator oldZoneChange = replacement.replacedByThis();

			EventFactory reveal = new EventFactory(EventType.REVEAL, "That card is revealed");
			reveal.parameters.put(EventType.Parameter.CAUSE, CauseOf.instance(oldZoneChange));
			reveal.parameters.put(EventType.Parameter.OBJECT, OldObjectOf.instance(oldZoneChange));
			replacement.addEffect(reveal);

			replacement.changeDestination(LibraryOf.instance(enchantedPlayer), -1);

			this.addEffectPart(replacementEffectPart(replacement));
		}
	}

	public WheelofSunandMoon(GameState state)
	{
		super(state);

		// Enchant player
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Player(state));

		// If a card would be put into enchanted player's graveyard from
		// anywhere, instead that card is revealed and put on the bottom of that
		// player's library.
		this.addAbility(new WheelofSunandMoonAbility1(state));
	}
}
