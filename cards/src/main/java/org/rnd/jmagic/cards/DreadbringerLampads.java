package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dreadbringer Lampads")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.NYMPH})
@ManaCost("4B")
@ColorIdentity({Color.BLACK})
public final class DreadbringerLampads extends Card
{
	public static final class DreadbringerLampadsAbility0 extends EventTriggeredAbility
	{
		public DreadbringerLampadsAbility0(GameState state)
		{
			super(state, "Constellation \u2014 Whenever Dreadbringer Lampads or another enchantment enters the battlefield under your control, target creature gains intimidate until end of turn.");
			this.addPattern(constellation());
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Intimidate.class, "Target creature gains intimidate until end of turn."));
		}
	}

	public DreadbringerLampads(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(2);

		// Constellation \u2014 Whenever Dreadbringer Lampads or another
		// enchantment enters the battlefield under your control, target
		// creature gains intimidate until end of turn. (It can't be blocked
		// except by artifact creatures and/or creatures that share a color with
		// it.)
		this.addAbility(new DreadbringerLampadsAbility0(state));
	}
}
