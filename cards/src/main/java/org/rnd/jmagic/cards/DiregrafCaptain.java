package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Diregraf Captain")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.SOLDIER})
@ManaCost("1UB")
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class DiregrafCaptain extends Card
{
	public static final class DiregrafCaptainAbility2 extends EventTriggeredAbility
	{
		public DiregrafCaptainAbility2(GameState state)
		{
			super(state, "Whenever another Zombie you control dies, target opponent loses 1 life.");

			this.addPattern(whenXDies(RelativeComplement.instance(Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.ZOMBIE)), ABILITY_SOURCE_OF_THIS)));

			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
			this.addEffect(loseLife(target, 1, "Target opponent loses 1 life."));
		}
	}

	public DiregrafCaptain(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Deathtouch
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));

		// Other Zombie creatures you control get +1/+1.
		SetGenerator otherZombieCreatures = RelativeComplement.instance(Intersect.instance(CreaturePermanents.instance(), HasSubType.instance(SubType.ZOMBIE)), This.instance());
		SetGenerator yourZombies = Intersect.instance(otherZombieCreatures, ControlledBy.instance(You.instance()));
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, yourZombies, "Other Zombie creatures you control", +1, +1, true));

		// Whenever another Zombie you control dies, target opponent loses 1
		// life.
		this.addAbility(new DiregrafCaptainAbility2(state));
	}
}
