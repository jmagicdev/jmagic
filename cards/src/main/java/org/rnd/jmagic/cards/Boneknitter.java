package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Boneknitter")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.CLERIC})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class Boneknitter extends Card
{
	public static final class BoneknitterAbility0 extends ActivatedAbility
	{
		public BoneknitterAbility0(GameState state)
		{
			super(state, "(1)(B): Regenerate target Zombie.");
			this.setManaCost(new ManaPool("(1)(B)"));
			Target target = this.addTarget(Intersect.instance(HasSubType.instance(SubType.ZOMBIE), Permanents.instance()), "target Zombie");
			this.addEffect(regenerate(targetedBy(target), "Regenerate target Zombie."));
		}
	}

	public Boneknitter(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (1)(B): Regenerate target Zombie.
		this.addAbility(new BoneknitterAbility0(state));

		// Morph (2)(B)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(2)(B)"));
	}
}
