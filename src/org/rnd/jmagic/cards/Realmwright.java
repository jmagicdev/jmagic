package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Realmwright")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.VEDALKEN})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class Realmwright extends Card
{
	public static final class RealmwrightAbility0 extends StaticAbility
	{
		public RealmwrightAbility0(GameState state)
		{
			super(state, "As Realmwright enters the battlefield, choose a basic land type.");

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, "Choose a land type");
			replacement.addPattern(asThisEntersTheBattlefield());

			SetGenerator originalEvent = replacement.replacedByThis();

			EventFactory factory = new EventFactory(EventType.PLAYER_CHOOSE, "Choose a land type.");
			factory.parameters.put(EventType.Parameter.PLAYER, ControllerOf.instance(originalEvent));
			factory.parameters.put(EventType.Parameter.CHOICE, Identity.instance(SubType.getBasicLandTypes()));
			factory.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.ENUM, PlayerInterface.ChooseReason.CHOOSE_BASIC_LAND_TYPE));
			factory.setLink(this);
			replacement.addEffect(factory);

			this.addEffectPart(replacementEffectPart(replacement));

			this.canApply = NonEmpty.instance();

			this.getLinkManager().addLinkClass(RealmwrightAbility1.class);
		}
	}

	public static final class RealmwrightAbility1 extends StaticAbility
	{
		public RealmwrightAbility1(GameState state)
		{
			super(state, "Lands you control are the chosen type in addition to their other types.");

			this.getLinkManager().addLinkClass(RealmwrightAbility0.class);
			SetGenerator chosenType = ChosenFor.instance(LinkedTo.instance(Identity.instance(this)));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Intersect.instance(LandPermanents.instance(), ControlledBy.instance(You.instance())));
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, chosenType);
			this.addEffectPart(part);
		}
	}

	public Realmwright(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// As Realmwright enters the battlefield, choose a basic land type.
		this.addAbility(new RealmwrightAbility0(state));

		// Lands you control are the chosen type in addition to their other
		// types.
		this.addAbility(new RealmwrightAbility1(state));
	}
}
