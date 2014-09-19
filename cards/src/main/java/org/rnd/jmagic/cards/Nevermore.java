package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nevermore")
@Types({Type.ENCHANTMENT})
@ManaCost("1WW")
@ColorIdentity({Color.WHITE})
public final class Nevermore extends Card
{
	public static final class NevermoreAbility0 extends StaticAbility
	{
		public NevermoreAbility0(GameState state)
		{
			super(state, "As Nevermore enters the battlefield, name a nonland card.");

			this.getLinkManager().addLinkClass(NevermoreAbility1.class);

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, "Name a nonland card");
			replacement.addPattern(asThisEntersTheBattlefield());

			SetGenerator originalEvent = replacement.replacedByThis();

			EventFactory factory = new EventFactory(PithingNeedle.NameACard.DAMMIT_ANOTHER_CUSTOM_EVENTTYPE, "Name a nonland card.");
			factory.parameters.put(EventType.Parameter.CAUSE, CauseOf.instance(originalEvent));
			factory.parameters.put(EventType.Parameter.PLAYER, ControllerOf.instance(originalEvent));
			factory.parameters.put(EventType.Parameter.CHOICE, NonLandCardNames.instance());
			factory.parameters.put(EventType.Parameter.SOURCE, Identity.instance(this));
			replacement.addEffect(factory);

			this.addEffectPart(replacementEffectPart(replacement));

			this.canApply = NonEmpty.instance();
		}
	}

	public static final class NevermoreAbility1 extends StaticAbility
	{
		public NevermoreAbility1(GameState state)
		{
			super(state, "The named card can't be cast.");

			SetGenerator name = ChosenFor.instance(LinkedTo.instance(Identity.instance(this)));
			PlayProhibition cast = new PlayProhibition(Players.instance(), (c, set) -> set.contains(c.name), name);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(cast));
			this.addEffectPart(part);

			this.getLinkManager().addLinkClass(NevermoreAbility0.class);
		}
	}

	public Nevermore(GameState state)
	{
		super(state);

		// As Nevermore enters the battlefield, name a nonland card.
		this.addAbility(new NevermoreAbility0(state));

		// The named card can't be cast.
		this.addAbility(new NevermoreAbility1(state));
	}
}
