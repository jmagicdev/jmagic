package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mwonvuli Beast Tracker")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SCOUT})
@ManaCost("1GG")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class MwonvuliBeastTracker extends Card
{
	public static final class MwonvuliBeastTrackerAbility0 extends EventTriggeredAbility
	{
		public MwonvuliBeastTrackerAbility0(GameState state)
		{
			super(state, "When Mwonvuli Beast Tracker enters the battlefield, search your library for a creature card with deathtouch, hexproof, reach, or trample and reveal it. Shuffle your library and put that card on top of it.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator hasKeywords = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Deathtouch.class, org.rnd.jmagic.abilities.keywords.Hexproof.class, org.rnd.jmagic.abilities.keywords.Reach.class, org.rnd.jmagic.abilities.keywords.Trample.class);

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a creature card with deathtouch, hexproof, reach, or trample and reveal it. Shuffle your library and put that card on top of it.");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.INDEX, numberGenerator(1));
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			search.parameters.put(EventType.Parameter.TO, LibraryOf.instance(You.instance()));
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(hasKeywords));
			this.addEffect(search);
		}
	}

	public MwonvuliBeastTracker(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// When Mwonvuli Beast Tracker enters the battlefield, search your
		// library for a creature card with deathtouch, hexproof, reach, or
		// trample and reveal it. Shuffle your library and put that card on top
		// of it.
		this.addAbility(new MwonvuliBeastTrackerAbility0(state));
	}
}
