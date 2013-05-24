package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Curse of Misfortunes")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.CURSE, SubType.AURA})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class CurseofMisfortunes extends Card
{
	public static final class CurseofMisfortunesAbility1 extends EventTriggeredAbility
	{
		public CurseofMisfortunesAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, you may search your library for a Curse card that doesn't have the same name as a Curse attached to enchanted player, put it onto the battlefield attached to that player, then shuffle your library.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator inYourLibrary = InZone.instance(LibraryOf.instance(You.instance()));
			SetGenerator curses = HasSubType.instance(SubType.CURSE);
			SetGenerator cursesInYourLibrary = Intersect.instance(inYourLibrary, curses);
			SetGenerator enchantedPlayer = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);
			SetGenerator attachedToEnchantedPlayer = AttachedTo.instance(enchantedPlayer);
			SetGenerator cursesAttachedToEnchantedPlayer = Intersect.instance(curses, attachedToEnchantedPlayer);
			SetGenerator namesOfCursesAttached = NameOf.instance(cursesAttachedToEnchantedPlayer);
			SetGenerator restriction = RelativeComplement.instance(cursesInYourLibrary, HasName.instance(namesOfCursesAttached));

			EventFactory search = new EventFactory(EventType.SEARCH, "Search your library for a Curse card that doesn't have the same name as a Curse attached to enchanted player,");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			search.parameters.put(EventType.Parameter.CARD, LibraryOf.instance(You.instance()));
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(restriction));

			EventFactory move = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_ATTACHED_TO, "put it onto the battlefield attached to that player,");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			move.parameters.put(EventType.Parameter.OBJECT, EffectResult.instance(search));
			move.parameters.put(EventType.Parameter.TARGET, enchantedPlayer);

			EventFactory shuffle = shuffleYourLibrary("then shuffle your library.");

			this.addEffect(youMay(sequence(search, move, shuffle)));
		}
	}

	public CurseofMisfortunes(GameState state)
	{
		super(state);

		// Enchant player
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Player(state));

		// At the beginning of your upkeep, you may search your library for a
		// Curse card that doesn't have the same name as a Curse attached to
		// enchanted player, put it onto the battlefield attached to that
		// player, then shuffle your library.
		this.addAbility(new CurseofMisfortunesAbility1(state));
	}
}
