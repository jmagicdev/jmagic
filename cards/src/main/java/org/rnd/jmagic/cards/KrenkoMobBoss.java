package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Krenko, Mob Boss")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.WARRIOR})
@ManaCost("2RR")
@ColorIdentity({Color.RED})
public final class KrenkoMobBoss extends Card
{
	public static final class KrenkoMobBossAbility0 extends ActivatedAbility
	{
		public KrenkoMobBossAbility0(GameState state)
		{
			super(state, "(T): Put X 1/1 red Goblin creature tokens onto the battlefield, where X is the number of Goblins you control.");
			this.costsTap = true;

			CreateTokensFactory factory = new CreateTokensFactory(Count.instance(Intersect.instance(HasSubType.instance(SubType.GOBLIN), ControlledBy.instance(You.instance()))), "Put two 1/1 red Goblin creature tokens onto the battlefield.");
			factory.addCreature(1, 1);
			factory.setColors(Color.RED);
			factory.setSubTypes(SubType.GOBLIN);
			this.addEffect(factory.getEventFactory());
		}
	}

	public KrenkoMobBoss(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// (T): Put X 1/1 red Goblin creature tokens onto the battlefield, where
		// X is the number of Goblins you control.
		this.addAbility(new KrenkoMobBossAbility0(state));
	}
}
