package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Solitary Confinement")
@Types({Type.ENCHANTMENT})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Judgment.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class SolitaryConfinement extends Card
{
	public static final class SolitaryConfinementAbility0 extends EventTriggeredAbility
	{
		public SolitaryConfinementAbility0(GameState state)
		{
			super(state, "At the beginning of your upkeep, sacrifice Solitary Confinement unless you discard a card.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			EventFactory sacThis = sacrificeThis("Solitary Confinement");
			EventFactory discard = discardCards(You.instance(), 1, "Discard a card");
			this.addEffect(unless(You.instance(), sacThis, discard, "Sacrifice Solitary Confinement unless you discard a card."));
		}
	}

	public static final class SolitaryConfinementAbility1 extends StaticAbility
	{
		public SolitaryConfinementAbility1(GameState state)
		{
			super(state, "Skip your draw step.");

			SimpleEventPattern yourDrawStep = new SimpleEventPattern(EventType.BEGIN_STEP);
			yourDrawStep.put(EventType.Parameter.STEP, DrawStepOf.instance(You.instance()));

			ReplacementEffect skip = new EventReplacementEffect(state.game, "Skip your draw step.", yourDrawStep);
			// no effects in the RE, replaces draw step with nothing
			this.addEffectPart(replacementEffectPart(skip));
		}
	}

	public static final class SolitaryConfinementAbility2 extends StaticAbility
	{
		public SolitaryConfinementAbility2(GameState state)
		{
			super(state, "You have shroud.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_ABILITY_TO_PLAYER);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(new org.rnd.jmagic.engine.SimpleAbilityFactory(org.rnd.jmagic.abilities.keywords.Shroud.class)));
			this.addEffectPart(part);
		}
	}

	public static final class SolitaryConfinementAbility3 extends StaticAbility
	{
		public SolitaryConfinementAbility3(GameState state)
		{
			super(state, "Prevent all damage that would be dealt to you.");

			this.addEffectPart(replacementEffectPart(new org.rnd.jmagic.abilities.PreventAllTo(state.game, You.instance(), "you")));
		}
	}

	public SolitaryConfinement(GameState state)
	{
		super(state);

		// At the beginning of your upkeep, sacrifice Solitary Confinement
		// unless you discard a card.
		this.addAbility(new SolitaryConfinementAbility0(state));

		// Skip your draw step.
		this.addAbility(new SolitaryConfinementAbility1(state));

		// You have shroud. (You can't be the target of spells or abilities.)
		this.addAbility(new SolitaryConfinementAbility2(state));

		// Prevent all damage that would be dealt to you.
		this.addAbility(new SolitaryConfinementAbility3(state));
	}
}
