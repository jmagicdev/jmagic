package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bitterheart Witch")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SHAMAN})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class BitterheartWitch extends Card
{
	public static final class BitterheartWitchAbility1 extends EventTriggeredAbility
	{
		public BitterheartWitchAbility1(GameState state)
		{
			super(state, "When Bitterheart Witch dies, you may search your library for a Curse card, put it onto the battlefield attached to target player, then shuffle your library.");
			this.addPattern(whenThisDies());

			SetGenerator you = You.instance();
			EventFactory search = new EventFactory(EventType.SEARCH, "Search your library for a Curse card");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.PLAYER, you);
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			search.parameters.put(EventType.Parameter.CARD, LibraryOf.instance(you));
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasSubType.instance(SubType.CURSE)));

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			EventFactory put = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_ATTACHED_TO, "put it onto the battlefield attached to target player");
			put.parameters.put(EventType.Parameter.CAUSE, This.instance());
			put.parameters.put(EventType.Parameter.CONTROLLER, you);
			put.parameters.put(EventType.Parameter.OBJECT, EffectResult.instance(search));
			put.parameters.put(EventType.Parameter.TARGET, target);

			EventFactory shuffle = shuffleYourLibrary("then shuffle your library.");

			this.addEffect(youMay(sequence(search, put, shuffle), "You may search your library for a Curse card, put it onto the battlefield attached to target player, then shuffle your library."));
		}
	}

	public BitterheartWitch(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Deathtouch
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));

		// When Bitterheart Witch dies, you may search your library for a Curse
		// card, put it onto the battlefield attached to target player, then
		// shuffle your library.
		this.addAbility(new BitterheartWitchAbility1(state));
	}
}
