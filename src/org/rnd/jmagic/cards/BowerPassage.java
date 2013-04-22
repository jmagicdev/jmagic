package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bower Passage")
@Types({Type.ENCHANTMENT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class BowerPassage extends Card
{
	public static final class BowerPassageAbility0 extends StaticAbility
	{
		public BowerPassageAbility0(GameState state)
		{
			super(state, "Creatures with flying can't block creatures you control.");

			SetGenerator hasFlying = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class);
			SetGenerator restriction = Intersect.instance(hasFlying, Blocking.instance(CREATURES_YOU_CONTROL));

			ContinuousEffect.Part cantBlock = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			cantBlock.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(restriction));
			this.addEffectPart(cantBlock);
		}
	}

	public BowerPassage(GameState state)
	{
		super(state);

		// Creatures with flying can't block creatures you control.
		this.addAbility(new BowerPassageAbility0(state));
	}
}
