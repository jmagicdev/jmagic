package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Judge's Familiar")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("(W/U)")
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class JudgesFamiliar extends Card
{
	public static final class JudgesFamiliarAbility1 extends ActivatedAbility
	{
		public JudgesFamiliarAbility1(GameState state)
		{
			super(state, "Sacrifice Judge's Familiar: Counter target instant or sorcery spell unless its controller pays (1).");
			this.addCost(sacrificeThis("Judge's Familiar"));

			Target target = this.addTarget(Intersect.instance(Spells.instance(), HasType.instance(Type.INSTANT, Type.SORCERY)), "target instant or sorcery spell");
			this.addEffect(counterTargetUnlessControllerPays("(1)", target));
		}
	}

	public JudgesFamiliar(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Sacrifice Judge's Familiar: Counter target instant or sorcery spell
		// unless its controller pays (1).
		this.addAbility(new JudgesFamiliarAbility1(state));
	}
}
