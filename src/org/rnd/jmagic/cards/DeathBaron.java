package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Death Baron")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.ZOMBIE})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class DeathBaron extends Card
{
	public static final class UndeadPump extends StaticAbility
	{
		public UndeadPump(GameState state)
		{
			super(state, "Skeleton creatures you control and other Zombie creatures you control get +1/+1 and have deathtouch.");

			SetGenerator objects = Intersect.instance(CreaturePermanents.instance(), Intersect.instance(ControlledBy.instance(You.instance()), Union.instance(HasSubType.instance(SubType.SKELETON), RelativeComplement.instance(HasSubType.instance(SubType.ZOMBIE), This.instance()))));

			this.addEffectPart(modifyPowerAndToughness(objects, 1, 1));

			this.addEffectPart(addAbilityToObject(objects, org.rnd.jmagic.abilities.keywords.Deathtouch.class));
		}
	}

	public DeathBaron(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new UndeadPump(state));
	}
}
