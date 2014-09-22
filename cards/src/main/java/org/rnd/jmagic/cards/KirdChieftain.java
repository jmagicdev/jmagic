package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kird Chieftain")
@Types({Type.CREATURE})
@SubTypes({SubType.APE})
@ManaCost("3R")
@ColorIdentity({Color.RED, Color.GREEN})
public final class KirdChieftain extends Card
{
	public static final class KirdChieftainAbility0 extends StaticAbility
	{
		public KirdChieftainAbility0(GameState state)
		{
			super(state, "Kird Chieftain gets +1/+1 as long as you control a Forest.");

			// Kird Chieftain gets +1/+1
			this.addEffectPart(modifyPowerAndToughness(This.instance(), 1, 1));

			// as long as you control a Forest.
			SetGenerator youControl = ControlledBy.instance(You.instance());
			SetGenerator forests = HasSubType.instance(SubType.FOREST);
			SetGenerator youControlAForest = Intersect.instance(youControl, forests);
			this.canApply = Both.instance(youControlAForest, this.canApply);
		}
	}

	public static final class KirdChieftainAbility1 extends ActivatedAbility
	{
		public KirdChieftainAbility1(GameState state)
		{
			super(state, "(4)(G): Target creature gets +2/+2 and gains trample until end of turn.");
			this.setManaCost(new ManaPool("(4)(G)"));
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(ptChangeAndAbilityUntilEndOfTurn(target, +2, +2, "Target creature gets +2/+2 and gains trample until end of turn.", org.rnd.jmagic.abilities.keywords.Trample.class));
		}
	}

	public KirdChieftain(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Kird Chieftain gets +1/+1 as long as you control a Forest.
		this.addAbility(new KirdChieftainAbility0(state));

		// (4)(G): Target creature gets +2/+2 and gains trample until end of
		// turn. (If it would assign enough damage to its blockers to destroy
		// them, you may have it assign the rest of its damage to defending
		// player or planeswalker.)
		this.addAbility(new KirdChieftainAbility1(state));
	}
}
