package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Arachnus Spinner")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIDER})
@ManaCost("5G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class ArachnusSpinner extends Card
{
	public static final class ArachnusSpinnerAbility1 extends ActivatedAbility
	{
		public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("ArachnusSpinner", "Where would you like to search?", true);

		public ArachnusSpinnerAbility1(GameState state)
		{
			super(state, "Tap an untapped Spider you control: Search your graveyard and/or library for a card named Arachnus Web and put it onto the battlefield attached to target creature. If you search your library this way, shuffle it.");

			// Tap an untapped Spider you control
			EventFactory cost = new EventFactory(EventType.TAP_CHOICE, "Tap an untapped Spider you control");
			cost.parameters.put(EventType.Parameter.CAUSE, This.instance());
			cost.parameters.put(EventType.Parameter.PLAYER, You.instance());
			cost.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(Untapped.instance(), Intersect.instance(HasSubType.instance(SubType.SPIDER), ControlledBy.instance(You.instance()))));
			cost.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			this.addCost(cost);

			SetGenerator yourLibrary = LibraryOf.instance(You.instance());
			EventFactory whichZones = new EventFactory(EventType.PLAYER_CHOOSE, "Where would you like to search?");
			whichZones.parameters.put(EventType.Parameter.PLAYER, You.instance());
			whichZones.parameters.put(EventType.Parameter.NUMBER, Between.instance(1, 2));
			whichZones.parameters.put(EventType.Parameter.CHOICE, Union.instance(GraveyardOf.instance(You.instance()), yourLibrary));
			whichZones.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.STRING, REASON));
			this.addEffect(whichZones);

			EventFactory search = new EventFactory(EventType.SEARCH, "Search your graveyard and/or library for a card named Arachnus Web");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			search.parameters.put(EventType.Parameter.CARD, EffectResult.instance(whichZones));
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasName.instance("Arachnus Web")));
			this.addEffect(search);

			SetGenerator youSearched = EffectResult.instance(search);
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			EventFactory drop = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_ATTACHED_TO, "and put it onto the battlefield attached to target creature.");
			drop.parameters.put(EventType.Parameter.CAUSE, This.instance());
			drop.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			drop.parameters.put(EventType.Parameter.OBJECT, youSearched);
			drop.parameters.put(EventType.Parameter.TARGET, target);
			this.addEffect(drop);

			SetGenerator youSearchedYourLibrary = Intersect.instance(youSearched, yourLibrary);
			EventFactory conditionalShuffle = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If you search your library this way, shuffle it.");
			conditionalShuffle.parameters.put(EventType.Parameter.IF, youSearchedYourLibrary);
			conditionalShuffle.parameters.put(EventType.Parameter.THEN, Identity.instance(shuffleYourLibrary("Shuffle your library")));
			this.addEffect(conditionalShuffle);
		}
	}

	public ArachnusSpinner(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(7);

		// Reach (This creature can block creatures with flying.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));

		// Tap an untapped Spider you control: Search your graveyard and/or
		// library for a card named Arachnus Web and put it onto the battlefield
		// attached to target creature. If you search your library this way,
		// shuffle it.
		this.addAbility(new ArachnusSpinnerAbility1(state));
	}
}
