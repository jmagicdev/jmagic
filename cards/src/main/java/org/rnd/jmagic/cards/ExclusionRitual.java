package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Exclusion Ritual")
@Types({Type.ENCHANTMENT})
@ManaCost("4WW")
@ColorIdentity({Color.WHITE})
public final class ExclusionRitual extends Card
{
	public static final class ExclusionRitualAbility0 extends EventTriggeredAbility
	{
		public ExclusionRitualAbility0(GameState state)
		{
			super(state, "When Exclusion Ritual enters the battlefield, exile target nonland permanent.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(Permanents.instance(), LandPermanents.instance()), "target nonland permanent"));
			EventFactory factory = exile(target, "Exile target nonland permanent.");
			factory.setLink(this);
			this.addEffect(factory);

			this.getLinkManager().addLinkClass(ExclusionRitualAbility1.class);
		}
	}

	public static final class ExclusionRitualAbility1 extends StaticAbility
	{
		public ExclusionRitualAbility1(GameState state)
		{
			super(state, "Players can't cast spells with the same name as the exiled card.");

			SetGenerator name = ChosenFor.instance(LinkedTo.instance(Identity.instance(this)));
			PlayProhibition cast = new PlayProhibition(Players.instance(), (c, set) -> set.contains(c.name), name);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(cast));
			this.addEffectPart(part);

			this.getLinkManager().addLinkClass(ExclusionRitualAbility0.class);
		}
	}

	public ExclusionRitual(GameState state)
	{
		super(state);

		// Imprint \u2014 When Exclusion Ritual enters the battlefield, exile
		// target nonland permanent.
		this.addAbility(new ExclusionRitualAbility0(state));

		// Players can't cast spells with the same name as the exiled card.
		this.addAbility(new ExclusionRitualAbility1(state));
	}
}
