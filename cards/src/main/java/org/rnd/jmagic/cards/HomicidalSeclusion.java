package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Homicidal Seclusion")
@Types({Type.ENCHANTMENT})
@ManaCost("4B")
@ColorIdentity({Color.BLACK})
public final class HomicidalSeclusion extends Card
{
	public static final class HomicidalSeclusionAbility0 extends StaticAbility
	{
		public HomicidalSeclusionAbility0(GameState state)
		{
			super(state, "As long as you control exactly one creature, that creature gets +3/+1 and has lifelink.");
			this.canApply = Both.instance(this.canApply, Intersect.instance(Count.instance(CREATURES_YOU_CONTROL), numberGenerator(1)));
			this.addEffectPart(modifyPowerAndToughness(CREATURES_YOU_CONTROL, +3, +1));
			this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Lifelink.class));
		}
	}

	public HomicidalSeclusion(GameState state)
	{
		super(state);

		// As long as you control exactly one creature, that creature gets +3/+1
		// and has lifelink.
		this.addAbility(new HomicidalSeclusionAbility0(state));
	}
}
