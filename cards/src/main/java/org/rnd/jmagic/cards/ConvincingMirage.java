package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Convincing Mirage")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class ConvincingMirage extends Card
{
	// As Convincing Mirage enters the battlefield, choose a basic land type.
	public static final class LandTypeChoice extends StaticAbility
	{
		/**
		 * @eparam CAUSE: who the fuck is making us choose a basic land type
		 * @eparam PLAYER: the poor bastard who has to actually choose it
		 * @eparam SOURCE: the object to store the choice on
		 * @eparam RESULT: the combined fecal matter of 17 angry gods unioned
		 * with the chosen basic land type
		 */
		public static EventType JESUS_FUCK_ANOTHER_EVENTTYPE_WHAT_DO_I_LOOK_LIKE_THE_GOD_DAMN_POPE_OR_SOMETHING = new EventType("JESUS_FUCK_ANOTHER_EVENTTYPE_WHAT_DO_I_LOOK_LIKE_THE_GOD_DAMN_POPE_OR_SOMETHING")
		{
			@Override
			public Parameter affects()
			{
				return null;
			}

			@Override
			public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
			{
				Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
				java.util.List<SubType> chosen = player.choose(1, SubType.getBasicLandTypes(), PlayerInterface.ChoiceType.ENUM, PlayerInterface.ChooseReason.CHOOSE_BASIC_LAND_TYPE);
				Linkable link = parameters.get(Parameter.SOURCE).getOne(Linkable.class).getPhysical();
				for(Object object: chosen)
					link.getLinkManager().addLinkInformation(object);
				event.setResult(Identity.fromCollection(chosen));
				return chosen.size() == 1;
			}
		};

		public LandTypeChoice(GameState state)
		{
			super(state, "As Convincing Mirage enters the battlefield, choose a basic land type.");

			this.getLinkManager().addLinkClass(ChangeLandType.class);

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, "As Convincing Mirage enters the battlefield, choose a basic land type.");
			replacement.addPattern(asThisEntersTheBattlefield());

			SetGenerator originalEvent = replacement.replacedByThis();

			EventFactory factory = new EventFactory(JESUS_FUCK_ANOTHER_EVENTTYPE_WHAT_DO_I_LOOK_LIKE_THE_GOD_DAMN_POPE_OR_SOMETHING, "Choose a basic land type.");
			factory.parameters.put(EventType.Parameter.CAUSE, CauseOf.instance(originalEvent));
			factory.parameters.put(EventType.Parameter.PLAYER, ControllerOf.instance(originalEvent));
			factory.parameters.put(EventType.Parameter.SOURCE, Identity.instance(this));
			replacement.addEffect(factory);

			this.addEffectPart(replacementEffectPart(replacement));

			this.canApply = NonEmpty.instance();
		}
	}

	public static final class ChangeLandType extends StaticAbility
	{
		public ChangeLandType(GameState state)
		{
			super(state, "Enchanted land is the chosen type.");
			this.getLinkManager().addLinkClass(LandTypeChoice.class);

			// Enchanted land
			SetGenerator enchantedLand = EnchantedBy.instance(This.instance());

			// is the chosen type.
			SetGenerator chosenType = ChosenFor.instance(LinkedTo.instance(Identity.instance(this)));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.SET_TYPES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, enchantedLand);
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, chosenType);
			this.addEffectPart(part);
		}
	}

	public ConvincingMirage(GameState state)
	{
		super(state);

		// Enchant land
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Land(state));

		this.addAbility(new LandTypeChoice(state));
		this.addAbility(new ChangeLandType(state));
	}
}
