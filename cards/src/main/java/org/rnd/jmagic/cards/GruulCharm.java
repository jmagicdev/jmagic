package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gruul Charm")
@Types({Type.INSTANT})
@ManaCost("RG")
@ColorIdentity({Color.RED, Color.GREEN})
public final class GruulCharm extends Card
{
	public GruulCharm(GameState state)
	{
		super(state);

		// Choose one \u2014

		// Creatures without flying can't block this turn
		{
			SetGenerator withoutFlying = RelativeComplement.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Blocking.instance(), withoutFlying)));
			this.addEffect(1, createFloatingEffect("Creatures without flying can't block this turn.", part));
		}

		// gain control of all permanents you own
		{
			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Intersect.instance(Permanents.instance(), OwnedBy.instance(You.instance())));
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			this.addEffect(2, createFloatingEffect("Gain control of all permanents you own.", part));
		}

		// Gruul Charm deals 3 damage to each creature with flying.
		{
			SetGenerator flying = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class);
			SetGenerator creaturesWithFlying = Intersect.instance(CreaturePermanents.instance(), flying);
			this.addEffect(3, spellDealDamage(3, creaturesWithFlying, "Gruul Charm deals 3 damage to each creature with flying."));
		}
	}
}
