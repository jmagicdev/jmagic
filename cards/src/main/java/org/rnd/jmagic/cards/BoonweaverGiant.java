package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Boonweaver Giant")
@Types({Type.CREATURE})
@SubTypes({SubType.MONK, SubType.GIANT})
@ManaCost("6W")
@ColorIdentity({Color.WHITE})
public final class BoonweaverGiant extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("BoonweaverGiant", "Choose zones to search", true);

	public static final class BoonweaverGiantAbility0 extends EventTriggeredAbility
	{
		public BoonweaverGiantAbility0(GameState state)
		{
			super(state, "When Boonweaver Giant enters the battlefield, you may search your graveyard, hand, and/or library for an Aura card and put it onto the battlefield attached to Boonweaver Giant. If you search your library this way, shuffle it.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator yourLibrary = LibraryOf.instance(You.instance());
			SetGenerator zones = Union.instance(GraveyardOf.instance(You.instance()), HandOf.instance(You.instance()), yourLibrary);
			EventFactory chooseZones = playerChoose(You.instance(), Between.instance(0, null), zones, PlayerInterface.ChoiceType.STRING, REASON, "You may");
			this.addEffect(chooseZones);

			SetGenerator chosenZones = EffectResult.instance(chooseZones);
			EventFactory search = new EventFactory(EventType.SEARCH, "search your graveyard, hand, and/or library for an Aura card");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			search.parameters.put(EventType.Parameter.CARD, chosenZones);
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasSubType.instance(SubType.AURA)));
			this.addEffect(search);

			SetGenerator thatAura = EffectResult.instance(search);
			EventFactory drop = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_ATTACHED_TO, "and put it onto the battlefield attached to Boonweaver Giant.");
			drop.parameters.put(EventType.Parameter.CAUSE, This.instance());
			drop.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			drop.parameters.put(EventType.Parameter.OBJECT, thatAura);
			drop.parameters.put(EventType.Parameter.TARGET, ABILITY_SOURCE_OF_THIS);
			this.addEffect(drop);

			SetGenerator searchedLibrary = Intersect.instance(chosenZones, yourLibrary);
			EventFactory maybeShuffle = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If you search your library this way, shuffle it.");
			maybeShuffle.parameters.put(EventType.Parameter.IF, searchedLibrary);
			maybeShuffle.parameters.put(EventType.Parameter.THEN, Identity.instance(shuffleLibrary(You.instance(), "Shuffle your library.")));
			this.addEffect(maybeShuffle);
		}
	}

	public BoonweaverGiant(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// When Boonweaver Giant enters the battlefield, you may search your
		// graveyard, hand, and/or library for an Aura card and put it onto the
		// battlefield attached to Boonweaver Giant. If you search your library
		// this way, shuffle it.
		this.addAbility(new BoonweaverGiantAbility0(state));
	}
}
