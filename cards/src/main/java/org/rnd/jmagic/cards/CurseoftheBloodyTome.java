package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Curse of the Bloody Tome")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.CURSE, SubType.AURA})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class CurseoftheBloodyTome extends Card
{
	public static final class CurseoftheBloodyTomeAbility1 extends EventTriggeredAbility
	{
		public CurseoftheBloodyTomeAbility1(GameState state)
		{
			super(state, "At the beginning of enchanted player's upkeep, that player puts the top two cards of his or her library into his or her graveyard.");

			SetGenerator enchantedPlayer = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, UpkeepStepOf.instance(enchantedPlayer));
			this.addPattern(pattern);

			this.addEffect(millCards(enchantedPlayer, 2, "That player puts the top two cards of his or her library into his or her graveyard."));
		}
	}

	public CurseoftheBloodyTome(GameState state)
	{
		super(state);

		// Enchant player
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Player(state));

		// At the beginning of enchanted player's upkeep, that player puts the
		// top two cards of his or her library into his or her graveyard.
		this.addAbility(new CurseoftheBloodyTomeAbility1(state));
	}
}
