package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mad Auntie")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.GOBLIN})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class MadAuntie extends Card
{
	public static final class GoblinPump extends StaticAbility
	{
		public GoblinPump(GameState state)
		{
			super(state, "Other Goblin creatures you control get +1/+1.");

			SetGenerator objects = RelativeComplement.instance(Intersect.instance(CreaturePermanents.instance(), Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.GOBLIN))), This.instance());

			this.addEffectPart(modifyPowerAndToughness(objects, 1, 1));
		}
	}

	public static final class AnAuntiesCaringTouch extends ActivatedAbility
	{
		public AnAuntiesCaringTouch(GameState state)
		{
			super(state, "(T): Regenerate another target Goblin.");
			this.costsTap = true;
			Target target = this.addTarget(RelativeComplement.instance(HasSubType.instance(SubType.GOBLIN), ABILITY_SOURCE_OF_THIS), "target Goblin other than Mad Auntie");
			this.addEffect(regenerate(targetedBy(target), "Regenerate another target Goblin."));
		}
	}

	public MadAuntie(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new GoblinPump(state));

		this.addAbility(new AnAuntiesCaringTouch(state));
	}
}
