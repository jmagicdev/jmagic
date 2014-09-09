package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Utopia Sprawl")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class UtopiaSprawl extends Card
{
	public static final class UtopiaSprawlAbility0 extends org.rnd.jmagic.abilities.keywords.Enchant
	{
		public UtopiaSprawlAbility0(GameState state)
		{
			super(state, "Forest", Intersect.instance(LandPermanents.instance(), HasSubType.instance(SubType.FOREST)));
		}
	}

	public static final class UtopiaSprawlAbility1 extends StaticAbility
	{
		public UtopiaSprawlAbility1(GameState state)
		{
			super(state, "As Utopia Sprawl enters the battlefield, choose a color.");

			this.getLinkManager().addLinkClass(UtopiaSprawlAbility2.class);

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, "Choose a color");
			replacement.addPattern(asThisEntersTheBattlefield());

			SetGenerator originalEvent = replacement.replacedByThis();

			EventFactory factory = new EventFactory(EventType.PLAYER_CHOOSE, "Choose a color.");
			factory.parameters.put(EventType.Parameter.PLAYER, ControllerOf.instance(originalEvent));
			factory.parameters.put(EventType.Parameter.CHOICE, Identity.fromCollection(Color.allColors()));
			factory.parameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.COLOR, PlayerInterface.ChooseReason.CHOOSE_COLOR));
			factory.parameters.put(EventType.Parameter.OBJECT, This.instance());
			factory.setLink(this);
			replacement.addEffect(factory);

			this.addEffectPart(replacementEffectPart(replacement));

			this.canApply = NonEmpty.instance();
		}
	}

	public static final class UtopiaSprawlAbility2 extends EventTriggeredAbility
	{
		public UtopiaSprawlAbility2(GameState state)
		{
			super(state, "Whenever enchanted Forest is tapped for mana, its controller adds one mana of the chosen color to his or her mana pool.");
			this.getLinkManager().addLinkClass(UtopiaSprawlAbility1.class);

			SetGenerator enchantedLand = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);
			this.addPattern(tappedForMana(Players.instance(), new SimpleSetPattern(enchantedLand)));

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			parameters.put(EventType.Parameter.MANA, ChosenFor.instance(LinkedTo.instance(This.instance())));
			parameters.put(EventType.Parameter.PLAYER, ControllerOf.instance(enchantedLand));
			this.addEffect(new EventFactory(EventType.ADD_MANA, parameters, "Its controller adds one mana of the chosen color to his or her mana pool."));
		}
	}

	public UtopiaSprawl(GameState state)
	{
		super(state);

		// Enchant Forest
		this.addAbility(new UtopiaSprawlAbility0(state));

		// As Utopia Sprawl enters the battlefield, choose a color.
		this.addAbility(new UtopiaSprawlAbility1(state));

		// Whenever enchanted Forest is tapped for mana, its controller adds one
		// mana of the chosen color to his or her mana pool (in addition to the
		// mana the land produces).
		this.addAbility(new UtopiaSprawlAbility2(state));
	}
}
