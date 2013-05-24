package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Runed Halo")
@Types({Type.ENCHANTMENT})
@ManaCost("WW")
@Printings({@Printings.Printed(ex = Expansion.SHADOWMOOR, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class RunedHalo extends Card
{
	public static final class RunedHaloAbility0 extends StaticAbility
	{
		public RunedHaloAbility0(GameState state)
		{
			super(state, "As Runed Halo enters the battlefield, name a card.");

			this.getLinkManager().addLinkClass(RunedHaloAbility1.class);

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, "Name a card");
			replacement.addPattern(asThisEntersTheBattlefield());

			SetGenerator originalEvent = replacement.replacedByThis();

			EventFactory factory = new EventFactory(PithingNeedle.NameACard.DAMMIT_ANOTHER_CUSTOM_EVENTTYPE, "Name a card.");
			factory.parameters.put(EventType.Parameter.CAUSE, CauseOf.instance(originalEvent));
			factory.parameters.put(EventType.Parameter.PLAYER, ControllerOf.instance(originalEvent));
			factory.parameters.put(EventType.Parameter.CHOICE, CardNames.instance());
			factory.parameters.put(EventType.Parameter.SOURCE, Identity.instance(this));
			replacement.addEffect(factory);

			this.addEffectPart(replacementEffectPart(replacement));

			this.canApply = NonEmpty.instance();
		}
	}

	public static final class RunedHaloAbility1 extends StaticAbility
	{
		public RunedHaloAbility1(GameState state)
		{
			super(state, "You have protection from the chosen name.");

			this.getLinkManager().addLinkClass(RunedHaloAbility0.class);

			AbilityFactory factory = new org.rnd.jmagic.abilities.keywords.Protection.AbilityFactory(HasName.instance(ChosenFor.instance(LinkedTo.instance(Identity.instance(this)))), "the name chosen for Runed Halo");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_ABILITY_TO_PLAYER);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(factory));
			this.addEffectPart(part);
		}
	}

	public RunedHalo(GameState state)
	{
		super(state);

		// As Runed Halo enters the battlefield, name a card.
		this.addAbility(new RunedHaloAbility0(state));

		// You have protection from the chosen name.
		this.addAbility(new RunedHaloAbility1(state));
	}
}
