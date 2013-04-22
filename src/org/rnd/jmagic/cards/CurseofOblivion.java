package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Curse of Oblivion")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.CURSE, SubType.AURA})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class CurseofOblivion extends Card
{
	public static final class CurseofOblivionAbility1 extends EventTriggeredAbility
	{
		public CurseofOblivionAbility1(GameState state)
		{
			super(state, "At the beginning of enchanted player's upkeep, that player exiles two cards from his or her graveyard.");

			SetGenerator enchantedPlayer = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, UpkeepStepOf.instance(enchantedPlayer));
			this.addPattern(pattern);

			SetGenerator cardsInGraveyard = Intersect.instance(Cards.instance(), InZone.instance(GraveyardOf.instance(enchantedPlayer)));
			this.addEffect(exile(enchantedPlayer, cardsInGraveyard, 2, "That player exiles two cards from his or her graveyard."));
		}
	}

	public CurseofOblivion(GameState state)
	{
		super(state);

		// Enchant player
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Player(state));

		// At the beginning of enchanted player's upkeep, that player exiles two
		// cards from his or her graveyard.
		this.addAbility(new CurseofOblivionAbility1(state));
	}
}
