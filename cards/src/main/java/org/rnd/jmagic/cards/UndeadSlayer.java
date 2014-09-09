package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Undead Slayer")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.CLERIC})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class UndeadSlayer extends Card
{
	public static final class Slay extends ActivatedAbility
	{
		public Slay(GameState state)
		{
			super(state, "(W), (T): Exile target Skeleton, Vampire, or Zombie.");

			this.setManaCost(new ManaPool("W"));
			this.costsTap = true;

			SetGenerator targettablePermanents = Intersect.instance(Permanents.instance(), HasSubType.instance(SubType.SKELETON, SubType.VAMPIRE, SubType.ZOMBIE));
			Target target = this.addTarget(targettablePermanents, "target Skeleton, Vampire, or Zombie");
			this.addEffect(exile(targetedBy(target), "Exile target Skeleton, Vampire, or Zombie."));
		}
	}

	public UndeadSlayer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new Slay(state));
	}
}
