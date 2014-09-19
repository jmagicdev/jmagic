package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Meddling Mage")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("WU")
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class MeddlingMage extends Card
{
	public static final class NameACard extends StaticAbility
	{
		public NameACard(GameState state)
		{
			super(state, "As Meddling Mage enters the battlefield, name a nonland card.");

			this.getLinkManager().addLinkClass(ProhibitCasting.class);

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

	public static final class ProhibitCasting extends StaticAbility
	{
		public ProhibitCasting(GameState state)
		{
			super(state, "The named card can't be cast.");

			SetGenerator name = ChosenFor.instance(LinkedTo.instance(Identity.instance(this)));

			PlayProhibition cast = new PlayProhibition(Players.instance(), (c, set) -> set.contains(c.name), name);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(cast));
			this.addEffectPart(part);

			this.getLinkManager().addLinkClass(NameACard.class);
		}
	}

	public MeddlingMage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// As Meddling Mage enters the battlefield, name a nonland card.
		this.addAbility(new NameACard(state));

		// The named card can't be cast.
		this.addAbility(new ProhibitCasting(state));
	}
}
