package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tablet of the Guilds")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class TabletoftheGuilds extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("TabletOfTheGuilds", "Choose two colors.", true);

	public static final class TabletoftheGuildsAbility0 extends StaticAbility
	{
		public TabletoftheGuildsAbility0(GameState state)
		{
			super(state, "As Tablet of the Guilds enters the battlefield, choose two colors.");

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, "Choose two colors");
			replacement.addPattern(asThisEntersTheBattlefield());

			SetGenerator originalEvent = replacement.replacedByThis();

			EventFactory factory = new EventFactory(EventType.PLAYER_CHOOSE, "Choose two colors.");
			factory.parameters.put(EventType.Parameter.PLAYER, ControllerOf.instance(originalEvent));
			factory.parameters.put(EventType.Parameter.CHOICE, Identity.instance(Color.allColors()));
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			factory.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.COLOR, REASON));
			factory.setLink(this);
			replacement.addEffect(factory);

			this.addEffectPart(replacementEffectPart(replacement));

			this.canApply = NonEmpty.instance();

			this.getLinkManager().addLinkClass(TabletoftheGuildsAbility1.class);
		}
	}

	public static final class TabletoftheGuildsAbility1 extends EventTriggeredAbility
	{
		public TabletoftheGuildsAbility1(GameState state)
		{
			super(state, "Whenever you cast a spell, if it's at least one of the chosen colors, you gain 1 life for each of the chosen colors it is.");

			this.addPattern(whenYouCastASpell());

			this.getLinkManager().addLinkClass(TabletoftheGuildsAbility0.class);

			SetGenerator thatSpell = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.OBJECT);
			SetGenerator chosenColors = ChosenFor.instance(LinkedTo.instance(This.instance()));
			SetGenerator chosenColorsItIs = Intersect.instance(chosenColors, ColorsOf.instance(thatSpell));

			this.interveningIf = chosenColorsItIs;

			this.addEffect(gainLife(You.instance(), Count.instance(chosenColorsItIs), "You gain 1 life for each of the chosen colors it is."));
		}
	}

	public TabletoftheGuilds(GameState state)
	{
		super(state);

		// As Tablet of the Guilds enters the battlefield, choose two colors.
		this.addAbility(new TabletoftheGuildsAbility0(state));

		// Whenever you cast a spell, if it's at least one of the chosen colors,
		// you gain 1 life for each of the chosen colors it is.
		this.addAbility(new TabletoftheGuildsAbility1(state));
	}
}
