package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Pithing Needle")
@Types({Type.ARTIFACT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.RARE), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SAVIORS_OF_KAMIGAWA, r = Rarity.RARE)})
@ColorIdentity({})
public final class PithingNeedle extends Card
{
	public static final class NameACard extends StaticAbility
	{
		/**
		 * @eparam CAUSE: the reason for naming a card
		 * @eparam PLAYER: the player naming a card
		 * @eparam CHOICE: the card names the player can choose from
		 * @eparam SOURCE: the object to store the choice on
		 * @eparam RESULT: the named card
		 */
		public static final EventType DAMMIT_ANOTHER_CUSTOM_EVENTTYPE = new EventType("DAMMIT_ANOTHER_CUSTOM_EVENTTYPE")
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
				java.util.List<String> named = player.choose(1, parameters.get(Parameter.CHOICE).getAll(String.class), PlayerInterface.ChoiceType.STRING, PlayerInterface.ChooseReason.NAME_A_CARD);
				Linkable link = parameters.get(Parameter.SOURCE).getOne(Linkable.class).getPhysical();
				for(Object object: named)
					link.getLinkManager().addLinkInformation(object);
				event.setResult(Identity.instance(named));
				return named.size() == 1;
			}
		};

		public NameACard(GameState state)
		{
			super(state, "As Pithing Needle enters the battlefield, name a card.");

			this.getLinkManager().addLinkClass(ProhibitAbilities.class);

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, "Name a card");
			replacement.addPattern(asThisEntersTheBattlefield());

			SetGenerator originalEvent = replacement.replacedByThis();

			EventFactory factory = new EventFactory(DAMMIT_ANOTHER_CUSTOM_EVENTTYPE, "Name a card.");
			factory.parameters.put(EventType.Parameter.CAUSE, CauseOf.instance(originalEvent));
			factory.parameters.put(EventType.Parameter.PLAYER, ControllerOf.instance(originalEvent));
			factory.parameters.put(EventType.Parameter.CHOICE, CardNames.instance());
			factory.parameters.put(EventType.Parameter.SOURCE, Identity.instance(this));
			replacement.addEffect(factory);

			this.addEffectPart(replacementEffectPart(replacement));

			this.canApply = NonEmpty.instance();
		}
	}

	// Activated abilities of sources with the chosen name can't be played
	// unless they're mana abilities.
	public static final class ProhibitAbilities extends StaticAbility
	{
		public ProhibitAbilities(GameState state)
		{
			super(state, "Activated abilities of sources with the chosen name can't be played unless they're mana abilities.");

			SetGenerator hasChosenName = HasName.instance(ChosenFor.instance(LinkedTo.instance(Identity.instance(this))));

			SimpleEventPattern prohibitPattern = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			prohibitPattern.put(EventType.Parameter.OBJECT, new ActivatedAbilitiesOfPattern(hasChosenName, false));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));
			this.addEffectPart(part);

			this.getLinkManager().addLinkClass(NameACard.class);
		}
	}

	public PithingNeedle(GameState state)
	{
		super(state);

		this.addAbility(new NameACard(state));
		this.addAbility(new ProhibitAbilities(state));
	}
}
